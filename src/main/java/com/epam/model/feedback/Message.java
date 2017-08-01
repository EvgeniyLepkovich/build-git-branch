package com.epam.model.feedback;

import org.eclipse.jgit.revwalk.RevCommit;

public class Message {
    private RevCommit commit;
    private String output;
    private String result;
    private int status;

    public RevCommit getCommit() {
        return commit;
    }

    public void setCommit(RevCommit commit) {
        this.commit = commit;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (status != message.status) return false;
        if (commit != null ? !commit.equals(message.commit) : message.commit != null) return false;
        if (output != null ? !output.equals(message.output) : message.output != null) return false;
        return result != null ? result.equals(message.result) : message.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = commit != null ? commit.hashCode() : 0;
        result1 = 31 * result1 + (output != null ? output.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + status;
        return result1;
    }

    @Override
    public String toString() {
        return "Message{" +
                "commit=" + commit +
                ", output='" + output + '\'' +
                ", result='" + result + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
