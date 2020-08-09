package com.gao.autoconfigure.test.autoconfigure;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public@ interface AspectLog {}