package com.mieczkowskidev.hanoikitties;


import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Stack;

public class Kitty extends Sprite{
    private int mWeight;
    private Stack mStack;
    private Sprite mBlock;

    public Kitty(int mWeight, float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager){
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.mWeight = mWeight;
    }
    public int getmWeight(){
        return mWeight;
    }
    public Stack getmStack(){
        return mStack;
    }
    public void setmStack(Stack mStack){
        this.mStack = mStack;
    }
    public Sprite getmBlock(){
        return mBlock;
    }
    public void setmBlock(Sprite mBlock){
        this.mBlock = mBlock;
    }
}
