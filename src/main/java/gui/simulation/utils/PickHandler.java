package gui.simulation.utils;

import java.awt.event.MouseEvent;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import gui.simulation.frame.MainFrame;

public class PickHandler {
	public int mouseX;
	public int mouseY;
	public int mode = GL2.GL_RENDER;
	private final MainFrame frame;
	public String pickedPlanet = " ";

	public PickHandler(MainFrame mainFrame) {
		this.frame = mainFrame;
	}

	public void pickHandle(GL2 gl, int x, int y) {
		final int bufferSize = 10;
		final int capacity = Buffers.SIZEOF_INT * bufferSize;
		IntBuffer selectBuffer = Buffers.newDirectIntBuffer(capacity);

		gl.glSelectBuffer(selectBuffer.capacity(), selectBuffer);
		gl.glRenderMode(GL2.GL_SELECT);

		gl.glInitNames();

		int[] viewport = new int[4];
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		float[] projection = new float[16];
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection, 0);

		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();

		frame.glu.gluPickMatrix(x, viewport[3] - y, 1, 1, viewport, 0);

		gl.glMultMatrixf(projection, 0);

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

		frame.drawScene(gl);

		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glPopMatrix();

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

		final int hits = gl.glRenderMode(GL2.GL_RENDER);
		mode = GL2.GL_RENDER;

		processHits(hits, selectBuffer);
	}

	private void processHits(int hits, IntBuffer buffer) {
		int offset = 0;
		int names;
		float z1, z2;

		System.out.println(" HITS: " + hits);

		for (int i = 0; i < hits; i++) {
			System.out.println(" hit: " + (i + 1));
			names = buffer.get(offset);
			offset++;
			z1 = (float) buffer.get(offset) / 0x7fffffff;
			offset++;
			z2 = (float) buffer.get(offset) / 0x7fffffff;
			offset++;
			System.out.println(" number of names: " + names);
			System.out.println(" z1: " + z1);
			System.out.println(" z2: " + z2);
			System.out.println(" names: ");

			for (int j = 0; j < names; j++) {
				getPlanetName(buffer.get(offset));
				System.out.print("  " + buffer.get(offset));
				if (j == (names - 1)) {
					System.out.println("<-");
				} else {
					System.out.println();
				}
				offset++;
			}
		}
		System.out.println(" ");
	}

	private void getPlanetName(int i) {
		switch (i) {
		case 1 -> pickedPlanet = "Sun";
		case 2 -> pickedPlanet = "Mercury";
		case 3 -> pickedPlanet = "Venus";
		case 4 -> pickedPlanet = "Earth";
		case 5 -> pickedPlanet = "Moon";
		case 6 -> pickedPlanet = "Mars";
		default -> pickedPlanet = "0 planets";
		}
	}

	public void mousePressed(MouseEvent me) {
		mouseX = me.getX();
		mouseY = me.getY();
		mode = GL2.GL_SELECT;
	}
}
