package com.bn.box2d.sndls;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.graphics.Bitmap;


public class Box2DUtil 
{
	public static MyPolygonImg createPolygonImg
	(
		float x,
		float y,
	    float[][] vData,
        boolean isStatic,
        World world,
        Bitmap[] bm,
        float width,
        float height,
        GameView gv
    )
	{    

		PolygonShape shape = new PolygonShape();

		FixtureDef fixtureDef = new FixtureDef();
		float density = 0;
		if(isStatic)
		{
			fixtureDef.setDensity(0.0f);
		}   
		else
		{
			fixtureDef.setDensity(2.0f);
		}   

		fixtureDef.setFriction(0.8f);

		//这句话没有找到对应的替换语句
		//shape.dimensions = 0.5f;

		Vec2[] vertices = new Vec2[vData.length];
		int index = 0;
		for(float[] fa:vData)
		{
			vertices[index] = new Vec2(fa[0]/RATE, fa[1]/RATE);
			index = index+1;
		}
		shape.set(vertices, vertices.length);
		fixtureDef.shape = shape;

		BodyDef bodyDef = new BodyDef();   

		bodyDef.position.set(x/RATE, y/RATE);   

		Body bodyTemp= world.createBody(bodyDef); 

		bodyTemp.createFixture(fixtureDef);
		MyPolygonImg result=null;
		int m_type = 0;
		switch(m_type)
        {
          case 0:
        	  result=new BodyWood(bodyTemp,bm,width,height,gv);	
          break;
          case 1:
        	  result=new BodyIce(bodyTemp,bm,width,height,gv);	
          break;
          case 2:
        	  result=new BodyCat(bodyTemp,bm,width,height,gv);	
          break;
          default:
        	  result=new MyPolygonImg(bodyTemp,bm,width,height,gv);	
        }
		return result;
	}
}
