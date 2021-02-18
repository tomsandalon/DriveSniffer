package Presentation.Utils;

import java.io.File;
import java.util.Stack;

public class Path {
    Stack<String> stack;

    public Path(String rootPath) {
        this.stack = new Stack<>();
        this.stack.push(rootPath);
    }

    public boolean isRoot() {
        return stack.size() == 1;
    }

    public String getCurrent() {
        return this.stack.peek();
    }

    public String navigate(String name) {
        this.stack.push(this.stack.peek().concat(File.separator + name));
        return this.stack.peek();
    }

    public String goBack() {
        if (isRoot()) return null;
        stack.pop();
        return stack.peek();
    }
}
