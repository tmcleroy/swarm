package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import swarm.shared.Grid;
import swarm.shared.Hex;
import swarm.shared.Layout;
import swarm.shared.Point;
import java.util.ArrayList;

public class Swarm extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;
    ArrayList<ArrayList<Point>> hexPoints;

	@Override
	public void create () {
        shapeRenderer = new ShapeRenderer();
        hexPoints = new ArrayList<ArrayList<Point>>();

		Grid grid = new Grid(10, 20);
		Layout layout = new Layout(Layout.flat, new Point(20, 20), new Point(30, 400));
		for(Hex h : grid.cells.values()) {
			hexPoints.add(Layout.polygonCorners(layout, h));
		}
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
	}
	
	@Override
	public void dispose () {
        shapeRenderer.dispose();
	}
}
