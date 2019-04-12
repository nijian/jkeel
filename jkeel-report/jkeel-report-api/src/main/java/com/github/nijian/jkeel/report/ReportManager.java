package com.github.nijian.jkeel.report;

import com.github.nijian.jkeel.commons.ObjectHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ReportManager is responsible for report proxy initialization, and provides report export interface as well.
 *
 * @author nj
 * @since 0.0.2
 */
public final class ReportManager {

    private static Logger logger = LoggerFactory.getLogger(ReportManager.class);

    /**
     * default ReportNonPoolProxy name
     */
    private final static String DEFAULT = "_DEFAULT";

    /**
     * ReportManager singleton instance
     */
    private static ReportManager manager;

    /**
     * ReportNonPoolProxy map
     */
    private Map<String, ReportProxy> reportMap = new HashMap<>();

    /**
     * init flag
     */
    private boolean init = false;

    /**
     * private constructor
     */
    private ReportManager() {
    }

    /**
     * Get ReportManager instance
     *
     * @return ReportManager instance
     */
    public static ReportManager getInstance() {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    InputStream config = ReportManager.class.getResourceAsStream("/config.json");
                    if (config == null) {
                        throw new RuntimeException("The default config.json file is not found");
                    }
                    logger.info("Initialize ReportManager with default config.json from root classpath");
                    manager = getInstance(config);
                }
            }
        }
        return manager;
    }

    /**
     * Get ReportManager instance
     *
     * @param config config file
     * @return ReportManager instance
     */
    public static ReportManager getInstance(File config) {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    manager = new ReportManager();
                    try {
                        logger.info("Initialize ReportManager with config file: {}", config.getAbsolutePath());
                        manager.init(ObjectHolder.objectMapper.readValue(config, ReportConfig.class));
                    } catch (Exception e) {
                        logger.error("Failed to init ReportManager", e);
                        throw new RuntimeException("Failed to init ReportManager", e);
                    }
                }
            }
        }
        return manager;
    }

    /**
     * Get ReportManager instance
     *
     * @param config config stream
     * @return ReportManager instance
     */
    public static ReportManager getInstance(InputStream config) {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    manager = new ReportManager();
                    try {
                        logger.info("Initialize ReportManager with config input stream");
                        manager.init(ObjectHolder.objectMapper.readValue(config, ReportConfig.class));
                    } catch (Exception e) {
                        logger.error("Failed to init ReportManager", e);
                        throw new RuntimeException("Failed to init ReportManager", e);
                    } finally {
                        try {
                            if (config != null) {
                                config.close();
                            }
                        } catch (Exception ex) {
                            logger.warn("The config file input stream can't be close", ex);
                        }
                    }

                }
            }
        }
        return manager;
    }

    /**
     * Get ReportManager instance
     *
     * @param config config string
     * @return ReportManager instance
     */
    public static ReportManager getInstance(String config) {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    manager = new ReportManager();
                    try {
                        logger.info("Initialize ReportManager with config string:\t\n{}", config);
                        manager.init(ObjectHolder.objectMapper.readValue(config, ReportConfig.class));
                    } catch (Exception e) {
                        logger.error("Failed to init ReportManager", e);
                        throw new RuntimeException("Failed to init ReportManager", e);
                    }
                }
            }
        }
        return manager;
    }

    /**
     * Initialize ReportManager internally.
     *
     * @param reportConfig report configuration
     */
    private void init(ReportConfig reportConfig) {
        List<ReportProxyConfig> reportProxyConfigs = reportConfig.getReportProxyConfigs();
        if (reportProxyConfigs == null || reportProxyConfigs.size() == 0) {
            logger.error("ReportNonPoolProxy is not found, please check the configuration : \t\n{}", reportConfig);
            throw new RuntimeException("Missing ReportNonPoolProxy config");
        }

        for (ReportProxyConfig reportProxyConfig : reportProxyConfigs) {

            String reportProxyId = reportProxyConfig.getId();
            String clzName = reportProxyConfig.getClzName();
            String properties = reportProxyConfig.getProperties();

            ReportProxy reportProxy;
            try {
                reportProxy = (ReportProxy) Class.forName(clzName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                logger.error("Failed to load ReportNonPoolProxy", e);
                throw new RuntimeException("Failed to load ReportNonPoolProxy", e);
            }
            reportProxy.init(properties);

            // find first default report proxy
            if (reportProxyConfig.isDefault() != null && reportProxyConfig.isDefault() && !reportMap.containsKey(DEFAULT)) {
                reportMap.put(DEFAULT, reportProxy);
            } else {
                reportMap.put(reportProxyId, reportProxy);
            }
        }
        init = true;
        logger.info("Initialized ReportManager with config:\t\n{}", reportConfig);
    }

    /**
     * Get ReportNonPoolProxy id list.
     *
     * @return ReportNonPoolProxy identifier set
     */
    public Set<String> getReportProxyIds() {
        return reportMap.keySet();
    }

    /**
     * Export to output stream
     *
     * @param rptURI       report template URI
     * @param exportParams export parameters
     * @param outputStream output stream
     */
    public void exportToStream(String rptURI, String exportParams, OutputStream outputStream) {
        exportToStream(DEFAULT, rptURI, exportParams, outputStream);
    }

    /**
     * Export to output stream
     *
     * @param reportProxyId report proxy identifier
     * @param rptURI        report template URI
     * @param exportParams  export parameters
     * @param outputStream  output stream
     */
    public void exportToStream(String reportProxyId, String rptURI, String exportParams, OutputStream outputStream) {
        check(reportProxyId);
        reportMap.get(reportProxyId).exportToStream(rptURI, exportParams, outputStream);
    }

    /**
     * Export to file
     *
     * @param rptURI       report template URI
     * @param exportParams export parameters
     * @return ReportMeta
     */
    public ReportMeta exportToFile(String rptURI, String exportParams) {
        return exportToFile(DEFAULT, rptURI, exportParams);
    }

    /**
     * Export to file
     *
     * @param reportProxyId report proxy identifier
     * @param rptURI        report template URI
     * @param exportParams  export parameters
     * @return ReportMeta
     */
    public ReportMeta exportToFile(String reportProxyId, String rptURI, String exportParams) {
        check(reportProxyId);
        return reportMap.get(reportProxyId).exportToFile(rptURI, exportParams);
    }

    /**
     * Check the report proxy is available or not
     *
     * @param reportProxyId report proxy identifier
     */
    private void check(String reportProxyId) {
        if (!init) {
            logger.error("ReportManager has not been initialized");
            throw new RuntimeException("ReportManager has not been initialized");
        }
        if (!reportMap.containsKey(reportProxyId)) {
            logger.error("The ReportNonPoolProxy is not exist : {}", reportProxyId);
            throw new RuntimeException("The ReportNonPoolProxy is not exist : " + reportProxyId);
        }
    }

}
