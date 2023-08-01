package main;

import Entities.Circle;
import Entities.RedShapeDecorator;
import Entities.Shape;

public class test {
	public static void main(String[] args) {
		Shape circle = new Circle();
		circle.draw();
		System.out.println("------------------");
		RedShapeDecorator redCircleDecorator = new RedShapeDecorator(circle);
		redCircleDecorator.draw();
		System.out.println("------------------");
	}
}
