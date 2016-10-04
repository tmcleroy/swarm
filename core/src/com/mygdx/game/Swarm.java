package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import jdk.nashorn.internal.objects.annotations.Function;
import swarm.shared.*;
import swarm.shared.Point;
import swarm.shared.Predicate;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.*;

public class Swarm extends ApplicationAdapter {
    int frame = 0;
    ShapeRenderer shapeRenderer;
    ArrayList<ArrayList<Point>> hexPoints;
    Grid grid;
    Layout layout;
    Unit unit1;

	@Override
	public void create () {
        shapeRenderer = new ShapeRenderer();
        hexPoints = new ArrayList<ArrayList<Point>>();

        grid = new Grid(10, 20);
        layout = new Layout(Layout.flat, new Point(20, 20), new Point(30, 400));
		for(Hex h : grid.cells.values()) {
            System.out.println(h);
			hexPoints.add(Layout.polygonCorners(layout, h));
		}
        unit1 = new Unit(new Hex(6, -6, 0));


        Predicate truePred = () -> true;
        Predicate falsePred = () -> false;

        Behavior b = Behavior.moveUp;
        System.out.println(b.behave());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        for (ArrayList<Point> points : hexPoints) {
            ArrayList<Float> pointList = new ArrayList<Float>();
            for (Point p : points) {
                pointList.add((float)p.x);
                pointList.add((float)p.y);
            }
            float[] vertices = new float[pointList.size()];
            int i = 0;
            for (Float f : pointList) vertices[i++] = f;
            shapeRenderer.polygon(vertices);

        }
        shapeRenderer.end();

        if (frame % 20 == 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                unit1.move(Unit.northEast);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                unit1.move(Unit.southEast);
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                unit1.move(Unit.northWest);
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                unit1.move(Unit.southWest);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                unit1.move(Unit.north);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                unit1.move(Unit.south);
            }
        }
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 1, 1);

        Point unit1Origin = Layout.hexToPixel(layout, unit1.position);
        shapeRenderer.circle((float) unit1Origin.x, (float) unit1Origin.y, 15);

        ArrayList<Hex> neighbors = unit1.getNeighbors();
        for (Hex neighbor : neighbors) {
            Point neighborOrigin = Layout.hexToPixel(layout, neighbor);
            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.circle((float) neighborOrigin.x, (float) neighborOrigin.y, 10);
        }

        shapeRenderer.end();
        frame++;
	}
	
	@Override
	public void dispose () {
        shapeRenderer.dispose();
	}
}
