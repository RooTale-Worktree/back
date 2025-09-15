package com.LiveToon.s3.dto;

public record PresignResponse(String url, long expiresInSec) {}