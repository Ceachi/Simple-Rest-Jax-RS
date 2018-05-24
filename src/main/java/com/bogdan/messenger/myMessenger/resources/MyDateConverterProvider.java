package com.bogdan.messenger.myMessenger.resources;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Calendar;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

class MyDateConverterProvider implements ParamConverterProvider {
	 
	 //rawType = tipul care vrei sa il convertesti
	 // genericType = daca folosesti generice
	 // mai are o lista de anotati, PathParam etc.
	@Override
	public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
		if(rawType.getName().equals(MyDate.class.getName())) {
			return new ParamConverter<T>(){

				@Override
				public T fromString(String value) {
					Calendar requestedDate = Calendar.getInstance();
					if("tomarrow".equalsIgnoreCase(value)) {
						requestedDate.add(Calendar.DATE, 1);
					}else if("yesterday".equalsIgnoreCase(value)) {
						requestedDate.add(Calendar.DATE, -1);
					}
					MyDate myDate = new MyDate();
					myDate.setDate(requestedDate.get(Calendar.DATE));
					myDate.setMonth(requestedDate.get(Calendar.MONTH));
					myDate.setYear(requestedDate.get(Calendar.YEAR));
					return rawType.cast(myDate);
				}

				@Override
				public String toString(T mybean) {
					if(mybean == null) {
						return null;
					}
					return mybean.toString();
				}
				
			};
		}
		return null;
	}
	 
}