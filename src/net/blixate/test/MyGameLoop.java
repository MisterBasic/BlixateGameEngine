package net.blixate.test;


import net.blixate.engine.BlixateEngine;
import net.blixate.engine.graphics.RenderEngine;
import net.blixate.engine.handlers.GameLoop;
import net.blixate.engine.vectors.Vector2;

public class MyGameLoop implements GameLoop {
	
	/*
	 * Created by using OneLoneCoder (javidx9)'s Code-It-Yourself 3D Graphics tutorial.
	 * 
	 * This implements 3D graphics into the 2D Engine, but the engine itself is
	 * not a 3D engine.
	 */
	
	class Vector3 {
		public float x, y, z;
		
		public Vector3(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	class Triangle implements Cloneable{
		public Vector3[] vectors = new Vector3[3];
		
		public Triangle() {
			vectors[0] = new Vector3(0, 0, 0);
			vectors[1] = new Vector3(0, 0, 0);
			vectors[2] = new Vector3(0, 0, 0);
		}
		
		public Triangle(Vector3 v1, Vector3 v2, Vector3 v3) {
			vectors[0] = v1;
			vectors[1] = v2;
			vectors[2] = v3;
		}
		
		public Triangle clone() {
			return new Triangle(vectors[0], vectors[1], vectors[2]);
		}
	}
	
	class Mesh {
		public Triangle[] tris;
		
		public Mesh(int triCount) {
			tris = new Triangle[triCount];
		}
	}
	
	class Matrix {
		public float[][] data;
		
		public Matrix() {
			data = new float[4][4];
		}
	}
	
	Mesh meshCube;
	Matrix matProj;
	double fov;
	float far;
	float near;
	float aspectRatio;
	float fovRad;
	double theta;
	
	public Vector3 MultiplyMatrixVector(Vector3 i, Matrix m) {
		Vector3 o = new Vector3(0,0,0);
		o.x = i.x * m.data[0][0] + i.y * m.data[1][0] + i.z * m.data[2][0] + m.data[3][0];
		o.y = i.x * m.data[0][1] + i.y * m.data[1][1] + i.z * m.data[2][1] + m.data[3][1];
		o.z = i.x * m.data[0][2] + i.y * m.data[1][2] + i.z * m.data[2][2] + m.data[3][2];
		float w = i.x * m.data[0][3] + i.y * m.data[1][3] + i.z * m.data[2][3] + m.data[3][3];
		
		if(w != 0) {
			o.x  /= w; o.y /= w; o.z /= w;
		}
		
		return o;
	}
	
	public void setup() {
		// Create a mesh
		meshCube = new Mesh(12);
		// SOUTH
		meshCube.tris[0] = new Triangle(new Vector3(0, 0, 0), new Vector3(0, 1, 0), new Vector3(1, 1, 0));
		meshCube.tris[1] = new Triangle(new Vector3(0, 0, 0), new Vector3(1, 1, 0), new Vector3(1, 0, 0));
		// EAST
		meshCube.tris[2] = new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 0), new Vector3(1, 1, 1));
		meshCube.tris[3] = new Triangle(new Vector3(1, 0, 0), new Vector3(1, 1, 1), new Vector3(1, 0, 1));
		// NORTH
		meshCube.tris[4] = new Triangle(new Vector3(1, 0, 1), new Vector3(1, 1, 1), new Vector3(0, 1, 1));
		meshCube.tris[5] = new Triangle(new Vector3(1, 0, 1), new Vector3(0, 1, 1), new Vector3(0, 0, 1));
		// WEST
		meshCube.tris[6] = new Triangle(new Vector3(0, 0, 1), new Vector3(0, 1, 1), new Vector3(0, 1, 0));
		meshCube.tris[7] = new Triangle(new Vector3(0, 0, 1), new Vector3(0, 1, 0), new Vector3(0, 0, 0));
		// TOP
		meshCube.tris[8] = new Triangle(new Vector3(0, 1, 0), new Vector3(0, 1, 1), new Vector3(1, 1, 1));
		meshCube.tris[9] = new Triangle(new Vector3(0, 1, 0), new Vector3(1, 1, 1), new Vector3(1, 1, 0));
		// BOTTOM
		meshCube.tris[10] = new Triangle(new Vector3(1, 0, 1), new Vector3(0, 0, 1), new Vector3(0, 0, 0));
		meshCube.tris[11] = new Triangle(new Vector3(1, 0, 1), new Vector3(0, 0, 0), new Vector3(1, 0, 0));
		
		near = 0.1f;
		far = 1000f;
		fov = 90.0f;
		aspectRatio = (float)BlixateEngine.window().getHeight() / BlixateEngine.window().getWidth();
		fovRad = 1.0f / (float)Math.tan(fov * 0.5f / 180 * Math.PI);
		matProj = new Matrix();
		matProj.data[0][0] = (float) (aspectRatio * fovRad);
		matProj.data[1][1] = (float) fovRad;
		matProj.data[2][2] = far / (far - near);
		matProj.data[3][2] = (-far * near) / (far - near);
		matProj.data[2][3] = 1.0f;
		matProj.data[3][3] = 0.0f;
		theta = 0;
	}
	
	public void loop(double elapsedTime)
	{
		RenderEngine.clear();
		
		this.drawMesh(meshCube, elapsedTime);
		
		this.update();
	}
	
	public void drawMesh(Mesh mesh, double elapsedTime) {
		Matrix matRotZ = new Matrix(), matRotX = new Matrix();
		theta = theta < 1000 ? theta + (1f * elapsedTime) : 1f;
		matRotZ.data[0][0] = (float) Math.cos(theta);
		matRotZ.data[0][1] = (float) Math.sin(theta);
		matRotZ.data[1][0] = (float) -Math.sin(theta);
		matRotZ.data[1][1] = (float) Math.cos(theta);
		matRotZ.data[2][2] = 1f;
		matRotZ.data[3][3] = 1f;
		
		matRotX.data[0][0] = 1f;
		matRotX.data[1][1] = (float) Math.cos(theta * 0.5f);
		matRotX.data[1][2] = (float) Math.sin(theta * 0.5f);
		matRotX.data[2][1] = (float) -Math.sin(theta * 0.5f);
		matRotX.data[2][2] = (float) Math.cos(theta * 0.5f);
		matRotX.data[3][3] = 1f;
		
		for(Triangle tri : meshCube.tris) {
			Triangle triProjected, triTranslated, triRotatedZ, triRotatedZX;
			triProjected = new Triangle();
			triTranslated = new Triangle();
			triRotatedZ = new Triangle();
			triRotatedZX = new Triangle();
			
			triRotatedZ.vectors[0] = MultiplyMatrixVector(tri.vectors[0], matRotZ);
			triRotatedZ.vectors[1] = MultiplyMatrixVector(tri.vectors[1], matRotZ);
			triRotatedZ.vectors[2] = MultiplyMatrixVector(tri.vectors[2], matRotZ);
			
			triRotatedZX.vectors[0] = MultiplyMatrixVector(triRotatedZ.vectors[0], matRotX);
			triRotatedZX.vectors[1] = MultiplyMatrixVector(triRotatedZ.vectors[1], matRotX);
			triRotatedZX.vectors[2] = MultiplyMatrixVector(triRotatedZ.vectors[2], matRotX);
			
			triTranslated = triRotatedZX;
			triTranslated.vectors[0].z = triRotatedZX.vectors[0].z + 3;
			triTranslated.vectors[1].z = triRotatedZX.vectors[1].z + 3;
			triTranslated.vectors[2].z = triRotatedZX.vectors[2].z + 3;
			
			triProjected.vectors[0] = MultiplyMatrixVector(triTranslated.vectors[0], matProj);
			triProjected.vectors[1] = MultiplyMatrixVector(triTranslated.vectors[1], matProj);
			triProjected.vectors[2] = MultiplyMatrixVector(triTranslated.vectors[2], matProj);
			
			triProjected.vectors[0].x++;
			triProjected.vectors[1].x++;
			triProjected.vectors[2].x++;
			triProjected.vectors[0].y++;
			triProjected.vectors[1].y++;
			triProjected.vectors[2].y++;
			
			triProjected.vectors[0].x *= 0.5f * BlixateEngine.window().getWidth();
			triProjected.vectors[0].y *= 0.5f * BlixateEngine.window().getHeight(); 
			triProjected.vectors[1].x *= 0.5f * BlixateEngine.window().getWidth(); 
			triProjected.vectors[1].y *= 0.5f * BlixateEngine.window().getHeight(); 
			triProjected.vectors[2].x *= 0.5f * BlixateEngine.window().getWidth(); 
			triProjected.vectors[2].y *= 0.5f * BlixateEngine.window().getHeight(); 
			
			// Put it on the screen
			Vector2 vec1 = new Vector2(triProjected.vectors[0].x, triProjected.vectors[0].y);
			Vector2 vec2 = new Vector2(triProjected.vectors[1].x, triProjected.vectors[1].y);
			Vector2 vec3 = new Vector2(triProjected.vectors[2].x, triProjected.vectors[2].y);
			RenderEngine.drawTriangle(vec1, vec2, vec3);
		}
	}
	
	public void update() {
		BlixateEngine.window().setTitle("FPS: " + BlixateEngine.getFPS());
		BlixateEngine.window().update();}
}
