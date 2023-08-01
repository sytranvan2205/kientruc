package Entities;

public class RedShapeDecorator extends ShapeDecorator{

	public RedShapeDecorator(Shape decoratorShape) {
		super(decoratorShape);
	}
	
	@Override
	public void draw() {
		decoratorShape.draw();
		System.out.println("red border");
	}

}
