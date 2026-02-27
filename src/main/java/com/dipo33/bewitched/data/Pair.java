package com.dipo33.bewitched.data;

import com.github.bsideup.jabel.Desugar;

@Desugar
public record Pair<T, U>(T first, U second) {
}
