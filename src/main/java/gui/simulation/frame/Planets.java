package gui.simulation.frame;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import gui.simulation.utils.PickHandler;
import gui.simulation.utils.TextureHandler;

public class Planets {
	private final PickHandler pickHandler;
	private final GLU glu;

	private final TextureHandler sunTexture;
	private final TextureHandler mercuryTexture;
	private final TextureHandler venusTexture;
	private final TextureHandler earthTexture;
	private final TextureHandler moonTexture;
	private final TextureHandler cloudsTexture;
	private final TextureHandler marsTexture;
	private final TextureHandler cubeTexture;

	private float mercuryRotation = .0f;
	private float venusRotation = .0f;
	private float earthRotation = .0f;
	private float marsRotation = .0f;
	private float moonRotation = .0f;

	public Planets(GL gl, GLU glu, PickHandler pickHandler) {
		this.pickHandler = pickHandler;
		this.glu = glu;
		cubeTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\stars.jpg", false);
		sunTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\sun.png", false);
		mercuryTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\mercury.jpg", false);
		venusTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\venus.jpg", false);
		earthTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\earth.jpg", false);
		moonTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\moon.jpg", false);
		cloudsTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\clouds.jpg", false);
		marsTexture = new TextureHandler(gl, glu, //
				"C:\\Users\\sovaa\\Desktop\\project\\images\\mars.jpg", false);
	}

	public void drawCube(GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(-20, -20f, -20);
		gl.glScalef(40, 40, 40);

		float[] vertices = { 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1 }; // 8 vertex
		int[] indices = { 0, 1, 2, 3, 0, 3, 4, 5, 0, 5, 6, 1, 1, 6, 7, 2, 7, 4, 3, 2, 4, 7, 6, 5 }; // 24 indices

		FloatBuffer bVertices = Buffers.newDirectFloatBuffer(vertices.length);
		for (float vertex : vertices)
			bVertices.put(vertex);
		bVertices.rewind();

		IntBuffer bIndices = Buffers.newDirectIntBuffer(indices.length);
		for (int index : indices)
			bIndices.put(index);
		bIndices.rewind();

		this.cubeTexture.bind();
		this.cubeTexture.enable();

		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

		gl.glVertexPointer(3, GL.GL_FLOAT, 0, bVertices);
		gl.glDrawElements(GL2.GL_QUADS, 24, GL.GL_UNSIGNED_INT, bIndices);

		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);

		this.cubeTexture.disable();
		gl.glPopMatrix();
	}

	public void createSun(GL2 gl) {
		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPushName(1);
		}

		gl.glPushMatrix();
		sunTexture.enable();
		sunTexture.bind();

		GLUquadric sun = glu.gluNewQuadric();
		glu.gluQuadricTexture(sun, true);
		glu.gluQuadricNormals(sun, GLU.GLU_SMOOTH);
		glu.gluSphere(sun, 1, 50, 50);
		glu.gluDeleteQuadric(sun);

		gl.glPopMatrix();

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPopName();
		}
	}

	public void createMercury(GL2 gl) {
		mercuryRotation += .5f;

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPushName(2);
		}

		gl.glPushMatrix();
		gl.glRotatef(mercuryRotation, 0.0f, 1f, 0.0f);
		gl.glTranslatef(1.5f, 0.0f, 0.0f);

		mercuryTexture.enable();
		mercuryTexture.bind();

		GLUquadric mercury = glu.gluNewQuadric();
		glu.gluQuadricTexture(mercury, true);
		glu.gluQuadricNormals(mercury, GLU.GLU_SMOOTH);
		glu.gluSphere(mercury, 0.2, 50, 50);
		glu.gluDeleteQuadric(mercury);

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPopName();
		}

		gl.glPopMatrix();
	}

	public void createVenus(GL2 gl) {
		venusRotation += .35f;

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPushName(3);
		}

		gl.glPushMatrix();
		gl.glRotatef(venusRotation, 0.0f, 1f, 0.0f);
		gl.glTranslatef(2.5f, 0.0f, 0.0f);

		venusTexture.enable();
		venusTexture.bind();

		GLUquadric venus = glu.gluNewQuadric();
		glu.gluQuadricTexture(venus, true);
		glu.gluQuadricNormals(venus, GLU.GLU_SMOOTH);
		glu.gluSphere(venus, 0.3, 50, 50);
		glu.gluDeleteQuadric(venus);

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPopName();
		}

		gl.glPopMatrix();
	}

	public void createEarth(GL2 gl) {
		earthRotation += .3f;

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPushName(4);
		}

		gl.glPushMatrix();
		gl.glRotatef(earthRotation, 0.0f, 1f, 0.0f);
		gl.glTranslatef(4f, 0.0f, 0.0f);

		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LESS);

		earthTexture.enable();
		earthTexture.bind();

		GLUquadric earth = glu.gluNewQuadric();
		glu.gluQuadricTexture(earth, true);
		glu.gluQuadricNormals(earth, GLU.GLU_SMOOTH);
		glu.gluSphere(earth, 0.3, 50, 50);
		glu.gluDeleteQuadric(earth);

		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		cloudsTexture.enable();
		cloudsTexture.bind();

		GLUquadric clouds = glu.gluNewQuadric();
		glu.gluQuadricTexture(clouds, true);
		glu.gluQuadricNormals(clouds, GLU.GLU_SMOOTH);
		glu.gluSphere(clouds, 0.31, 50, 50);
		glu.gluDeleteQuadric(clouds);

		gl.glDisable(GL2.GL_BLEND);

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPopName();
		}

		createMoon(gl);

		gl.glPopMatrix();
	}

	private void createMoon(GL2 gl) {
		moonRotation += .3f;

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPushName(5);
		}

		gl.glPushMatrix();
		gl.glRotatef(moonRotation, 0.0f, 1f, 0.0f);
		gl.glTranslatef(0.4f, 0.0f, 0.0f);

		moonTexture.enable();
		moonTexture.bind();

		GLUquadric moon = glu.gluNewQuadric();
		glu.gluQuadricTexture(moon, true);
		glu.gluQuadricNormals(moon, GLU.GLU_SMOOTH);
		glu.gluSphere(moon, 0.05, 50, 50);
		glu.gluDeleteQuadric(moon);

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPopName();
		}

		gl.glPopMatrix();
	}

	public void createMars(GL2 gl) {
		marsRotation += .3f;

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPushName(6);
		}

		gl.glPushMatrix();
		gl.glRotatef(marsRotation, 0.0f, 1f, 0.0f);
		gl.glTranslatef(7f, 0.0f, 0.0f);

		marsTexture.enable();
		marsTexture.bind();

		GLUquadric mars = glu.gluNewQuadric();
		glu.gluQuadricTexture(mars, true);
		glu.gluQuadricNormals(mars, GLU.GLU_SMOOTH);
		glu.gluSphere(mars, 0.25, 50, 50);
		glu.gluDeleteQuadric(mars);

		if (pickHandler.mode == GL2.GL_SELECT) {
			gl.glPopName();
		}

		gl.glPopMatrix();
	}
}
