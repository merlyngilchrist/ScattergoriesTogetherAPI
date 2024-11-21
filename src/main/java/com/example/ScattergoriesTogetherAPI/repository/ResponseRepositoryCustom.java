package com.example.ScattergoriesTogetherAPI.repository;

public interface ResponseRepositoryCustom {
    void markResponseAsValid(String responseId);
    void markResponseAsInvalid(String responseId);
}
