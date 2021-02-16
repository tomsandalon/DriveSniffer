package Presentation;

import Presentation.PresentationIObjects.IRootFolder;

public class Result {

    private final boolean success;

    private String errorMsg;

    private IRootFolder result;

    public Result(IRootFolder result) {
        this.result = result;
        this.success = true;
    }

    public Result(String errorMsg) {
        this.errorMsg = errorMsg;
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public IRootFolder getResult() {
        return result;
    }
}