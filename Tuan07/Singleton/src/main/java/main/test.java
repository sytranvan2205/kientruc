package main;

import Entities.Singleton;

class test {

	public static void main(String[] args) {
		System.out.println("-----Singleton pattern-----");
		Singleton singleton1 = Singleton.getInstance();
		Singleton singleton2 = Singleton.getInstance();
		if(singleton1.equals(singleton2)) {
			System.out.println("Unique Instace");
		}
	}

}
