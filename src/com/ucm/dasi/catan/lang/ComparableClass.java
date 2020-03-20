package com.ucm.dasi.catan.lang;

public class ComparableClass<T> implements Comparable<ComparableClass<?>> {

    private Class<T> innerClass;
    
    public ComparableClass(Class<T> innerClass) {
	this.innerClass = innerClass;
    }

    @Override
    public int compareTo(ComparableClass<?> other) {
	return innerClass.getCanonicalName().compareTo(other.innerClass.getCanonicalName());
    }
    
    public Class<T> getInnerClass() {
	return innerClass;
    }
    
}
