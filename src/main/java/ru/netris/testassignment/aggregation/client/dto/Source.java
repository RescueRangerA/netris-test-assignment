package ru.netris.testassignment.aggregation.client.dto;

public record Source(Type urlType, String videoUrl) {
    public enum Type {
        LIVE, ARCHIVE
    }
}
