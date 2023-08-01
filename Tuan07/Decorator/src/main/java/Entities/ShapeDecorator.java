package Entities;

public abstract class ShapeDecorator implements Shape{

	protected Shape decoratorShape;
	
	
	
	public ShapeDecorator(Shape decoratorShape) {
		super();
		this.decoratorShape = decoratorShape;
	}

	public void draw() {
		decoratorShape.draw();
	}

}
