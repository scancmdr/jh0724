package tooltime.controller;

import tooltime.service.Context;

public class ValidationException extends Exception {
    final String messageKey;

    public ValidationException(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessage() {
        var messageQ = Context.get().getConfigurationService().getMessage(messageKey);
        return messageQ.orElse("[undefined]");
    }
}
