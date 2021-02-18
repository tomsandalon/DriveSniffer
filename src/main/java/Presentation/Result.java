package Presentation;

import Presentation.PresentationIObjects.IRootFolder;

public class Result {

    private final boolean success;

    private String errorMsg = "";

    private IRootFolder result;

    public Result(IRootFolder result) {
        this.result = result;
        this.success = true;
    }

    public Result(String errorMsg) {
        this.errorMsg = errorMsg;
        this.success = false;
    }

    public Result(Result result, String errorMsg) {
        this.success = false;
        if (!result.isSuccess()) {
            this.errorMsg = result.getErrorMsg().concat("\n");
        }
        this.errorMsg = this.errorMsg.concat(errorMsg);
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