package com.Rootale.s3.dto;

public record PresignResponse(String url, long expiresInSec) {}