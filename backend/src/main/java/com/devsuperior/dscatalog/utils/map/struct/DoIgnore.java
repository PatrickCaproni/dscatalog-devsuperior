package com.devsuperior.dscatalog.utils.map.struct;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in ambiguous methods where both have the same input and output
 * https://mapstruct.org/faq/#ambiguous
 */
@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface DoIgnore {
}