package com.bogdan.messenger.myMessenger.resources;

/*ParamConverters (vrem sa convertim din stringul primit, in ce obiect vrem noi)
 * 
 * ParamConverterProvider =  Provider class ce ii spune lu Jersey sa foloseasca ParamConverter
 *  - se uita dupa anotarile cu Param
 *  ParamConverter <---  ParamConverter getConverter(Class rawType, ...) <-- Type
 *  
 *  
 * ParamConverter = interfata cu metode de a converti stringul in ce vrei tu
 * 		T fromString(String value)
 * DataType Instance <----- T fromString(String value) <-----  String
 *Exemplu:
*/
public class MyDate {
	private int date;
	private int month;
	private int year;
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "MyDate [date=" + date + ", month=" + month + ", year=" + year + "]";
	}
	
	
}
