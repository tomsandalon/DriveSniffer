package Logic.Concurrent.Objects;

public class ConcurrentRootFolder extends ConcurrentFolder{

    private boolean finalSize = false;
    public ConcurrentRootFolder(String path, ConcurrentFolder parent, String shortName) {
        super(path, parent, shortName);
    }

    public boolean isFinalSize() {
        return finalSize;
    }

    public void setIsFinalSize(boolean finalSize) {
        this.finalSize = finalSize;
    }
}
