package gui.simulation.frame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import gui.simulation.utils.Camera;
import gui.simulation.utils.PickHandler;

public class MainFrame extends JFrame implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	final int PARTS_NUM = 10;
	private GLCanvas canvas;
	private Animator animator;
	public GLU glu;
	private GLUT glut;
	private Planets creator;
	private PickHandler pickHandler;

	public MainFrame() {
		super("Simulation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1920, 1080);
		this.initializeJogl();
		this.setVisible(true);
		this.pickHandler = new PickHandler(this);
	}

	private void initializeJogl() {
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities capabilities = new GLCapabilities(glprofile);

		capabilities.setHardwareAccelerated(true);
		capabilities.setDoubleBuffered(true);

		capabilities.setNumSamples(2);
		capabilities.setSampleBuffers(true);

		this.canvas = new GLCanvas(capabilities);
		this.getContentPane().add(this.canvas);
		this.canvas.addGLEventListener(this);
		this.animator = new Animator(this.canvas);
		this.animator.start();
		this.glu = new GLU();

		this.canvas.addKeyListener(this);
		this.canvas.addMouseListener(this);
		this.canvas.addMouseMotionListener(this);
	}

	public void init(GLAutoDrawable canvas) {
		GL2 gl = canvas.getGL().getGL2();
		gl.glClearColor(0, 0, 0, 0);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

		glut = new GLUT();

		gl.glShadeModel(GL2.GL_SMOOTH);

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);

		creator = new Planets(canvas.getGL(), glu, pickHandler);

		gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
		gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
	}

	public void display(GLAutoDrawable canvas) {
		GL2 gl = canvas.getGL().getGL2();

		if (pickHandler.mode == GL2.GL_RENDER) {

			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			gl.glWindowPos2i(20, canvas.getSurfaceHeight() - 30); // Adjust the position of the string
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, pickHandler.pickedPlanet);
			this.drawScene(gl);
		} else {
			pickHandler.pickHandle(gl, pickHandler.mouseX, pickHandler.mouseY);
		}
	}

	public void drawScene(GL2 gl) {
		gl.glLoadIdentity();

		Camera.aimCamera(gl, glu);
		Camera.moveCamera();

		gl.glEnable(GL2.GL_TEXTURE_GEN_S);
		gl.glEnable(GL2.GL_TEXTURE_GEN_T);

		creator.createSun(gl);
		creator.createMercury(gl);
		creator.createVenus(gl);
		creator.createEarth(gl);
		creator.createMars(gl);

		creator.drawCube(gl);
	}

	public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
		GL2 gl = canvas.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		double ratio = (double) width / (double) height;
		glu.gluPerspective(38, ratio, 0.1, 100);

		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent event) {
		Camera.calculateEvent(event);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		pickHandler.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}