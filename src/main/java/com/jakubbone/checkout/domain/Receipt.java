package com.jakubbone.checkout.domain;

import java.util.List;

public record Receipt(List<ReceiptPosition> positions) {}
