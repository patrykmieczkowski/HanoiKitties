package com.mieczkowskidev.hanoikitties;

import android.graphics.Typeface;
import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class HanoiKitties extends SimpleBaseGameActivity {

    private static int CAMERA_WIDTH = 800;
    private static int CAMERA_HEIGHT = 480;
    private ITextureRegion mBackgroundImage, mBlockImage, mKitty1, mKitty2, mKitty3;
    private Sprite mBlock1, mBlock2, mBlock3;
    private Stack mStack1, mStack2, mStack3;
    final Scene scene = new Scene();
    int movesCount = 0;
    Text movesCountText;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources() {
        try {
            ITexture backgroundImage = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/background.png");
                }
            });
            ITexture blockImage = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/block.png");
                }
            });
            ITexture kitty1 = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/kitty1.png");
                }
            });
            ITexture kitty2 = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/kitty2.png");
                }
            });
            ITexture kitty3 = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/kitty3.png");
                }
            });
            backgroundImage.load();
            blockImage.load();
            kitty1.load();
            kitty2.load();
            kitty3.load();
            this.mBackgroundImage = TextureRegionFactory.extractFromTexture(backgroundImage);
            this.mBlockImage = TextureRegionFactory.extractFromTexture(blockImage);
            this.mKitty1 = TextureRegionFactory.extractFromTexture(kitty1);
            this.mKitty2 = TextureRegionFactory.extractFromTexture(kitty2);
            this.mKitty3 = TextureRegionFactory.extractFromTexture(kitty3);
            this.mStack1 = new Stack();
            this.mStack2 = new Stack();
            this.mStack3 = new Stack();
        } catch (IOException e) {
            Debug.e(e);
        }
    }

    @Override
    protected Scene onCreateScene() {
        Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundImage, getVertexBufferObjectManager());
        scene.attachChild(backgroundSprite);
        mBlock1 = new Sprite(192, 63, this.mBlockImage, getVertexBufferObjectManager());
        mBlock2 = new Sprite(400, 63, this.mBlockImage, getVertexBufferObjectManager());
        mBlock3 = new Sprite(604, 63, this.mBlockImage, getVertexBufferObjectManager());
        scene.attachChild(mBlock1);
        scene.attachChild(mBlock2);
        scene.attachChild(mBlock3);
        Kitty kitty1 = new Kitty(1, 139, 174, this.mKitty1, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (((Kitty) this.getmStack().peek()).getmWeight() != this.getmWeight())
                    return false;
                this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    BlockCollisionCheck(this);
                }
                return true;
            }
        };
        Kitty kitty2 = new Kitty(2, 118, 212, this.mKitty2, getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (((Kitty) this.getmStack().peek()).getmWeight() != this.getmWeight())
                    return false;
                this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    BlockCollisionCheck(this);
                }
                return true;
            }
        };
        Kitty kitty3 = new Kitty(3, 97, 255, this.mKitty3, getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (((Kitty) this.getmStack().peek()).getmWeight() != this.getmWeight())
                    return false;
                this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    BlockCollisionCheck(this);
                }
                return true;
            }
        };

        scene.attachChild(kitty1);
        scene.attachChild(kitty2);
        scene.attachChild(kitty3);

        this.mStack1.add(kitty3);
        this.mStack1.add(kitty2);
        this.mStack1.add(kitty1);
        kitty1.setmStack(mStack1);
        kitty2.setmStack(mStack1);
        kitty3.setmStack(mStack1);
        kitty1.setmBlock(mBlock1);
        kitty2.setmBlock(mBlock1);
        kitty3.setmBlock(mBlock1);
        scene.registerTouchArea(kitty1);
        scene.registerTouchArea(kitty2);
        scene.registerTouchArea(kitty3);
        scene.setTouchAreaBindingOnActionDownEnabled(true);

        Font main_font = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA, Typeface.DEFAULT, 30, true, Color.BLACK_ABGR_PACKED_INT);
        main_font.load();

        movesCountText = new Text(0, 0, main_font, "Moves:" + movesCount , this.getVertexBufferObjectManager());
        movesCountText.setPosition(10,10);
        scene.attachChild(movesCountText);

        return scene;
    }

    private void  BlockCollisionCheck(Kitty kitty){
        Stack stack = null;
        Sprite block = null;
        if (kitty.collidesWith(mBlock1) && (mStack1.size() == 0 || kitty.getmWeight() < ((Kitty) mStack1.peek()).getmWeight())) {
            stack = mStack1;
            block = mBlock1;
            movesCount = movesCount + 1;
            if(movesCountText != null) {
                movesCountText.setText("Moves: " + movesCount);
            }
        } else if (kitty.collidesWith(mBlock2) && (mStack2.size() == 0 || kitty.getmWeight() < ((Kitty) mStack2.peek()).getmWeight())) {
            stack = mStack2;
            block = mBlock2;
            movesCount = movesCount + 1;
                if(movesCountText != null) {
                    movesCountText.setText("Moves: " + movesCount);
                }
        } else if (kitty.collidesWith(mBlock3) && (mStack3.size() == 0 || kitty.getmWeight() < ((Kitty) mStack3.peek()).getmWeight())) {
            stack = mStack3;
            block = mBlock3;
            movesCount = movesCount + 1;
                    if(movesCountText != null) {
                        movesCountText.setText("Moves: " + movesCount);
                    }
        } else {
            stack = kitty.getmStack();
            block = kitty.getmBlock();
        }
        kitty.getmStack().remove(kitty);
        if (stack != null && block !=null && stack.size() == 0) {
            kitty.setPosition(block.getX() + block.getWidth()/2 - kitty.getWidth()/2, block.getY() + block.getHeight() - kitty.getHeight());
        } else if (stack != null && block !=null && stack.size() > 0) {
            kitty.setPosition(block.getX() + block.getWidth()/2 - kitty.getWidth()/2, ((Kitty) stack.peek()).getY() - kitty.getHeight());
        }
        stack.add(kitty);
        kitty.setmStack(stack);
        kitty.setmBlock(block);

        GameOver();
    }



    private void GameOver() {
        if (mStack3.size() == 3 ){
            Font main_font = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA, Typeface.DEFAULT, 60, true, Color.BLACK_ABGR_PACKED_INT);

            main_font.load();

            Text gameOverText = new Text(0, 0, main_font, "You Win!", this.getVertexBufferObjectManager());
            gameOverText.setPosition(CAMERA_WIDTH/2 - gameOverText.getWidth()/2, CAMERA_HEIGHT/2 - gameOverText.getHeight()/2);
            scene.attachChild(gameOverText);

            scene.clearTouchAreas();

        }
    }
}
