package sort;

import java.util.List;

public class  Message {
    private List<String> messageList;

    public Message() {
    }

    public Message(List<String> messageList) {
        this.messageList = messageList;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return "sort.Message{" +
                "messageList=" + messageList +
                '}';
    }
}
