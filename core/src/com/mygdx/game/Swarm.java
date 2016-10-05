package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import swarm.shared.*;
import swarm.shared.Point;
import java.util.ArrayList;

public class Swarm extends ApplicationAdapter {
    int frame = 0;
    int gridWidth = 40;     // number of tiles
    int gridHeight = 20;    // number of tiles
    int cellSize = 20;
    SpriteBatch batch;
    BitmapFont font;
    ShapeRenderer shapeRenderer;
    Grid grid;
    Layout layout;
    ArrayList<ArrayList<Point>> hexPoints;
    ArrayList<Unit> units = new ArrayList<>();

	@Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        hexPoints = new ArrayList<ArrayList<Point>>();

        grid = new Grid(gridWidth, gridHeight);
        layout = new Layout(Layout.flat, new Point(cellSize, cellSize), new Point(55, 700));
		for(Hex h : grid.cells.values()) {
            hexPoints.add(Layout.polygonCorners(layout, h));
		}
        units.add(new Unit(new Hex(6, -4, -2), Behavior.moveDown));
        units.add(new Unit(new Hex(6, -21, 15), Behavior.moveUp));
	}

	@Override
	public void render () {
        this.handleInput();
        this.handleUnitBehavior();
        this.renderBackground();
        this.renderMap(false);
        this.renderUnits();

        frame++;
	}
	
	@Override
	public void dispose () {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
	}

	private void handleInput() {
        if (frame % 20 == 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                units.get(0).move(Unit.northEast);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                units.get(0).move(Unit.southEast);
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                units.get(0).move(Unit.northWest);
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                units.get(0).move(Unit.southWest);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                units.get(0).move(Unit.north);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                units.get(0).move(Unit.south);
            }
        }
    }

    private void handleUnitBehavior() {
        if (frame % 60 == 0) {
            units.forEach(Unit::behave);
        }
    }

    private void renderBackground() {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

	private void renderMap(boolean renderCoords) {
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        int i = 0;
        for (ArrayList<Point> points : hexPoints) {
            ArrayList<Float> pointList = new ArrayList<Float>();
            for (Point p : points) {
                pointList.add((float)p.x);
                pointList.add((float)p.y);
            }
            float[] vertices = new float[pointList.size()];
            int j = 0;
            for (Float f : pointList) vertices[j++] = f;
            shapeRenderer.polygon(vertices);

            if (renderCoords) { // render coordinates of each cell (slows the game down)
                Hex h = grid.cells.values().toArray(new Hex[grid.cells.size()])[i++];
                batch.begin();
                font.setColor(Color.BLACK);
                font.getData().setScale(0.75f, 0.75f);
                font.draw(
                        batch,
                        h.toString(),
                        vertices[vertices.length - 2] - cellSize - 5,
                        vertices[vertices.length - 1] - (cellSize / 2)
                );
                batch.end();
            }
        }
        shapeRenderer.end();
    }

    private void renderUnits() {
        shapeRenderer.begin(ShapeType.Filled);
        for (Unit u : units) {
            shapeRenderer.setColor(0, 0, 1, 1);
            Point origin = Layout.hexToPixel(layout, u.position);
            shapeRenderer.circle((float) origin.x, (float) origin.y, 15);

            for (Hex neighbor : u.getNeighbors()) {
                Point neighborOrigin = Layout.hexToPixel(layout, neighbor);
                shapeRenderer.setColor(0, 1, 0, 1);
                shapeRenderer.circle((float) neighborOrigin.x, (float) neighborOrigin.y, 10);
            }
        }
        shapeRenderer.end();
    }
}
