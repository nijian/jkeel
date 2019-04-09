package com.github.nijian.jkeel.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ReportManager {

    private static Logger logger = LoggerFactory.getLogger(ReportManager.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * default printer id
     */
    private final static String DEFAULT = "_DEFAULT";

    /**
     * print client instance
     */
    private static ReportManager manager;

    /**
     * printer map
     */
    private Map<String, ReportPoolProxy> printerMap = new HashMap<>();

    /**
     * init flag of print client
     */
    private boolean inited = false;

    /**
     * private print client constructor
     */
    private ReportManager() {
    }

    /**
     * Get print client instance
     *
     * @return print client
     */
    public static ReportManager getInstance() {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    InputStream config = ReportManager.class.getResourceAsStream("/config.json");
                    if (config == null) {
                        throw new RuntimeException("The default config.json file is not found");
                    }
                    logger.info("Initialize print manager with default config.json from root classpath");
                    manager = getInstance(config);
                }
            }
        }
        return manager;
    }

    public static ReportManager getInstance(File config) {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    manager = new ReportManager();
                    try {
                        logger.info("Initialize print manager with config file: {}", config.getAbsolutePath());
                        manager.init0(objectMapper.readValue(config, ReportConfig.class));
                    } catch (Exception e) {
                        logger.error("An error has occurred", e);
                        throw new RuntimeException("Failed to init print manager", e);
                    }
                }
            }
        }
        return manager;
    }

    public static ReportManager getInstance(InputStream config) {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    manager = new ReportManager();
                    try {
                        logger.info("Initialize print manager with config input stream");
                        manager.init0(objectMapper.readValue(config, ReportConfig.class));
                    } catch (Exception e) {
                        logger.error("An error has occurred", e);
                        throw new RuntimeException("Failed to init print manager", e);
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

    public static ReportManager getInstance(String config) {
        if (manager == null) {
            synchronized (ReportManager.class) {
                if (manager == null) {
                    manager = new ReportManager();
                    try {
                        logger.info("Initialize print manager with config string:\t\n{}", config);
                        manager.init0(objectMapper.readValue(config, ReportConfig.class));
                    } catch (Exception e) {
                        logger.error("An error has occurred", e);
                        throw new RuntimeException("Failed to init print manager", e);
                    }
                }
            }
        }
        return manager;
    }

    /**
     * Initialize print client internally.
     *
     * @param printConfig print configuration
     */
    private void init0(ReportConfig printConfig) {
        List<ReportProxyConfig> printerConfigs = printConfig.getPrinterConfigs();
        if (printerConfigs == null || printerConfigs.size() == 0) {
            logger.error("Printer config is empty, please check: \t\n{}", printConfig);
            throw new RuntimeException("Missing printer config");
        }

        for (ReportProxyConfig printerConfig : printerConfigs) {
            String printerId = printerConfig.getId();
            String clzName = printerConfig.getClzName();
            String properties = printerConfig.getProperties();

            ReportPoolProxy printer;
            try {
                printer = (ReportPoolProxy) Class.forName(clzName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                logger.error("An error has occurred", e);
                throw new RuntimeException("Failed to load printer class", e);
            }
            printer.init(properties);
            //find first default printer
            if (printerConfig.isDefault() != null && printerConfig.isDefault() && !printerMap.containsKey(DEFAULT)) {
                printerMap.put(DEFAULT, printer);
            } else {
                printerMap.put(printerId, printer);
            }
        }
        inited = true;
        logger.info("Initialized print manager with config:\t\n{}", printConfig);
    }

    /**
     * Get printer id list.
     *
     * @return printer id list
     */
    public Set<String> getPrinterIds() {
        return printerMap.keySet();
    }


    public void printToStream(String rptURI, String printParams, OutputStream outputStream) throws Exception {
        printToStream(DEFAULT, rptURI, printParams, outputStream);
    }



    public void printToStream(String printerId, String rptURI, String printParams, OutputStream outputStream) throws Exception {
        checkPrinter(printerId);
        printerMap.get(printerId).printToStream(rptURI, printParams, outputStream);
    }

    public ReportMeta printToFile(String rptURI, String printParams) throws Exception {
        return printToFile(DEFAULT, rptURI, printParams);
    }

    public ReportMeta printToFile(String printerId, String rptURI, String printParams) throws Exception {
        checkPrinter(printerId);
        return printerMap.get(printerId).printToFile(rptURI, printParams);
    }

    private void checkPrinter(String printerId) {
        if (!inited) {
            throw new RuntimeException("Print Manager has not been initialized");
        }
        if (!printerMap.containsKey(printerId)) {
            throw new RuntimeException("The printer is not exist:" + printerId);
        }
    }

}
