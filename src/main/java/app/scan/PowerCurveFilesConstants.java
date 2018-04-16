package app.scan;

public enum PowerCurveFilesConstants {
    // path to save files location
    FILE_LIST_PATH("C:\\workspace\\IntelliJProjects\\bundle-info\\all-files.txt"),
    // project dir path
    PROJECT_LOCATION("C:\\workspace\\powercurve\\"),
    // ILightSecurityTokenContext, IRobotExecutionCallback, DocumentTemplateFontProvider, RowOutputData, WorkflowTransactionContex
    // Others - ISecurityService
    CLASS_INTERFACE_MATCHING("ISecurityService"),
    // class matching experssion
    CLASS_REGEXP_PATTERN("class\\s+\\w+\\s+implements(\\s+).*(%s)"),
    // class matching experssion
    INTERFACE_REGEXP_PATTERN("interface\\s+\\w+\\s+extends(\\s+).*(%s)");

    private final String value;

    PowerCurveFilesConstants(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
