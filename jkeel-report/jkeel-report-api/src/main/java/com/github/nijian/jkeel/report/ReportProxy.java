package com.github.nijian.jkeel.report;

import java.io.OutputStream;

public interface ReportProxy {

    void init(String properties);

    void exportToStream(String rptURI, String paramsJson, OutputStream outputStream);

    ReportMeta exportToFile(String rptURI, String paramsJson);

}
