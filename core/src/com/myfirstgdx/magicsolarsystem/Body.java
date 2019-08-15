package com.myfirstgdx.magicsolarsystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;

public class Body {
    static final float SIZE = 0.07f;

    String name;
    Vector2 currentPos = new Vector2();
    Vector2 position = new Vector2();
    int radius;
    Texture img;
    Body parent;
    List<Body> orbitingBodies;
    long fullTime = 1000000000;

    public Body(Vector2 pos, String name, int r){
        this.name = name;
        this.radius = r;
        img = new Texture(Constants.imgPath + name + ".png");
        this.position = pos;
        currentPos= pos;
        orbitingBodies = new LinkedList<Body>();
    }

    public Body(String name, int r, Body parent, long fullTime){
        this.name = name;
        this.radius = r;
        img = new Texture(Constants.imgPath + name + ".png");
        parent.add(this);
        orbitingBodies = new LinkedList<Body>();
        this.fullTime = fullTime;
    }

    public Body getContainsBody(String name){
        if (getName().equals(name))
            return this;
        for (Body body : orbitingBodies) {
            Body containsBody = body.getContainsBody(name);
            if (containsBody != null) {
                return containsBody;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
//
//    public Vector2 getCurPosition(int width, int height) {
//        return new Vector2(currentPos.x + (getWidth() - width) / 2, currentPos.y + (getHeight() - height) / 2);
//    }

    public Texture getImg() {
        return img;
    }

    public  float getWidth(){
        return img.getWidth()*SIZE;
    }

    public  float getHeight(){
        return img.getHeight()*SIZE;
    }

    public void setCurPosition(long delta) {
        currentPos = new Vector2(Math.round(position.x + radius * Math.cos(delta * Math.PI / fullTime / Constants.andTime )),
                Math.round(position.y + radius * Math.sin(delta * Math.PI / fullTime / Constants.andTime )));
    }
    public boolean add(Body newBody){
        String bodyName = newBody.getName();
        newBody.setPosition( position);
        if (getContainsBody(bodyName)==null){
            orbitingBodies.add(newBody);
            newBody.parent=this;
            return true;
        }
        return false;
    }

    public void dispose(){
        img.dispose();
        orbitingBodies.clear();
    }

    public void draw(SpriteBatch batch, long delta) {
        if (parent != null)
            position = parent.currentPos;
        setCurPosition(delta);
        batch.draw(img, currentPos.x - getWidth()/2 ,
                currentPos.y - getHeight() / 2,
                getWidth(), getHeight());
        for (Body body : orbitingBodies) {
            body.draw(batch, delta);
        }
    }
}