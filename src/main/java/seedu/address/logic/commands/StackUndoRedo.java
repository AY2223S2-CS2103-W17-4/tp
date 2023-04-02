package seedu.address.logic.commands;

import java.util.Stack;

/**
 * @@author zhixianggg reused
 * Solution adapted from
 * https://github.com/AY1920S1-CS2103T-T09-4/main/pull/161/commits/094fbe26c9917afa2cf3e6a4e2fc5808166e4f79
 */

public class StackUndoRedo {
    private Stack<RedoableCommand> undoStack;
    private Stack<RedoableCommand> redoStack;

    /**
     * StackUndoRedo Class to enable storing of commands FIFO
     */
    public StackUndoRedo() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Pushes {@code command} onto the undo-stack if it is of type {@code UndoableCommand}. Clears the redo-stack
     * if {@code command} is not of type {@code UndoCommand} or {@code RedoCommand}.
     */
    public void push(Command command) {
        if (!(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
            redoStack = new Stack();
        }

        if (!(command instanceof RedoableCommand)) {
            return;
        }

        undoStack.add((RedoableCommand) command);
    }

    /**
     * Pops and returns the next {@code UndoableCommand} to be undone in the stack.
     */
    public RedoableCommand popUndo() {
        redoStack.push(undoStack.pop());
        return redoStack.peek();
    }

    /**
     * Pops and returns the next {@code UndoableCommand} to be redone in the stack.
     */
    public RedoableCommand popRedo() {
        undoStack.push(redoStack.pop());
        return undoStack.peek();
    }

    /**
     * Returns true if there are more commands that can be undone.
     */
    public boolean canUndo() {
        return !undoStack.empty();
    }

    /**
     * Returns true if there are more commands that can be redone.
     */
    public boolean canRedo() {
        return !redoStack.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StackUndoRedo)) {
            return false;
        }

        StackUndoRedo stack = (StackUndoRedo) other;

        // state check
        return undoStack.equals(stack.undoStack)
                       && redoStack.equals(stack.redoStack);
    }
}
