package com.trikon.compy.screen;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.trikon.compy.Compy;
import com.trikon.compy.os.Computer;
import com.trikon.compy.os.Terminal;
import com.trikon.compy.os.TextBuffer;
import com.trikon.compy.os.TextStyle;
import com.trikon.compy.util.Device;
import com.trikon.compy.util.GuiRenderer;
import com.trikon.compy.util.TextRenderer;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.template.Template.Palette;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.input.ReversedLinesFileReader;

public class ComputerScreen extends Screen {

	public static int xSize = 300;
	public static int ySize = 200;
	private int w;
	private int h;
	private int x;
	private int y;
	private TextFieldWidget user;
	private final ResourceLocation GUI = new ResourceLocation(Compy.MODID, "textures/gui/screen.png");
	private Computer computer;
	private Device device;
	private MatrixStack matrixStack;
	private boolean waitForUpdate;
	private String[] outputStrings;

	// Input Track
	private String[] inputs;
	private int inputIndex;
	 protected TerminalContainer terminal;
	public ComputerScreen(Device d) {
		super(new TranslationTextComponent("screen.computer.display"));
		this.device = d;
		// this.computerScreen = new com.trikon.compy.os.core.Screen();
		// TODO Auto-generated constructor stub
		MatrixStack stack = new MatrixStack();
		this.computer = new Computer(d, this);
		System.out.println("GUI: " + GUI.getPath());
		terminal = new TerminalContainer( this.computer,
	            GuiRenderer.BORDER, GuiRenderer.BORDER, xSize, ySize, new TranslationTextComponent("container.terminal"));
	}

	@Override
	protected void init() {
		initOutputString();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		w = this.width;
		h = this.height;
		x = ((w - xSize) / 2) + 7;
		y = ((h - ySize) / 2) + 7;
		user = new TextFieldWidget(font, x, ySize + 30, 160, 20, new TranslationTextComponent("container.computer"));
		user.changeFocus(true);
		user.setMaxLength(32767);
		user.active = true;
		user.setEditable(true);
		user.setCanLoseFocus(false);
		user.setBordered(false);
		user.visible = true;
		user.setFocus(true);
		user.setValue(computer.commandLinePrefix());
		this.addButton(initTerminal());
		//this.children.add(user);

	}
	
	private TerminalContainer initTerminal() {
		return terminal;
	}
	@Override
	public void tick() {
		super.tick();
		this.user.tick();
		if (!user.getValue().startsWith(computer.commandLinePrefix())) {
			user.setValue(computer.commandLinePrefix());
		}
		try {
			getOutputFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.matrixStack = matrixStack;
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack,
				new StringTextComponent("*WIP*").setStyle(Style.EMPTY.withColor(Color.parseColor("red"))), mouseX,
				mouseY);
		w = this.width;
		h = this.height;
		int x1 = ((w - xSize) / 2) + 7;
		int y1 = ((h - ySize) / 2) + 7;
		//RenderSystem.color4f( 1, 1, 1, 1 );
		GL11.glColor4f(1,1,1,1);
		ComputerScreen.drawString(matrixStack, font, new StringTextComponent("ComPy OS v0.0.2").setStyle(TextStyle.COMPY), x1,y1, 10);
		ComputerScreen.drawString(matrixStack, font, new StringTextComponent("    ing").setStyle(TextStyle.COMPY), x1,y1+6, 11);
		ComputerScreen.drawString(matrixStack, font, new StringTextComponent("test").setStyle(TextStyle.OUTPUT), x1,y1+6, 10);

		IRenderTypeBuffer.Impl renderer = Minecraft.getInstance().renderBuffers().bufferSource();
		IVertexBuilder buffer = renderer.getBuffer( TextRenderer.Type.MAIN);
		TextRenderer.drawEmptyTerminal(TransformationMatrix.identity().getMatrix(), renderer, x, y, xSize, ySize);
		TextBuffer text = new TextBuffer("This is now working!");
		TextBuffer tex = new TextBuffer("This is now a working product!");
		TextRenderer.drawString(
                50, 1 * TextRenderer.FONT_HEIGHT, tex, tex, tex, com.trikon.compy.util.Palette.DEFAULT,
                false, 5, 5);
		TextRenderer.drawString(
                50, 2 * TextRenderer.FONT_HEIGHT, text, text, text, com.trikon.compy.util.Palette.DEFAULT,
                false, 5, 5);
		//renderer.endBatch();
			//this.renderOutput(matrixStack);
		//this.user.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (user.isFocused()) {
			user.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
		if (p_keyPressed_1_ == 265 && p_keyPressed_2_ == 328) { // Up

		}
		if (p_keyPressed_1_ == 264 && p_keyPressed_2_ == 336) { // Down

		}

		if (p_keyPressed_1_ == 257 && p_keyPressed_2_ == 28) {
			runCommand(user.getValue());
		}
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}

	private void runCommand(String cmd) {

		cmd = cmd.replace(computer.commandLinePrefix(), "");
		if(cmd.equals("python")) {
			updateOutput("Python 3.7");
			this.computer.setPythonConsole(true);
		}
		else if(cmd.equals("exit()")) {
			this.computer.setPythonConsole(false);
		}
		else if(this.computer.isPythonConsole()) {
			updateOutput(computer.commandLinePrefix() + cmd);
			this.computer.runPythonCommand(cmd, this);
		}
		else {
			this.computer.runCommand(cmd, this);
		}
		addInputLog(cmd);
		user.setValue(computer.commandLinePrefix());
	}
	private void addInputLog(String s) {
		File inputLog = new File(computer.getFileSystem().getEnvRootDir() + "\\.input");
		Writer output;
		if (inputLog.exists()) {
			try {
				output = new BufferedWriter(
						new FileWriter(computer.getFileSystem().getEnvRootDir() + "\\.input", true));
				output.append(s);
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // clears file every time

		} else {
			try {
				output = new BufferedWriter(new FileWriter(computer.getFileSystem().getEnvRootDir() + "\\.input"));
				output.write(s);
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void renderBackground(MatrixStack matrixStack) {
		Minecraft.getInstance().getTextureManager().getTexture(GUI);
		Minecraft.getInstance().getTextureManager().bind(GUI);
		int w = this.width;
		int h = this.height;
		int x = ((w - xSize) / 2);
		int y = ((h - ySize) / 2);

		//this.blit(matrixStack, x, y, 0, 0, xSize*2, ySize*2);
		//ComputerScreen.fill(matrixStack, x, y, xSize, ySize, 0xFF272727);
		//System.out.println("width: " + this.width + "  height: " + this.height);
		 //ComputerScreen.blit(matrixStack, x, y, 0, 0, xSize, xSize, 256, 256);
		// ComputerScreen.blit(matrixStack, x, y, 0, 0, xSize, ySize, 0, 2);
		// ComputerScreen.blit(matrixStack, x, y, 0, 0, xSize, ySize, 50, 50, 1, 1);
	}
	 
	private void renderOutput(MatrixStack matrixStack) {
		for (int i = 0; i < outputStrings.length; i++) {
			if (i > 0 && i < 17) {
				if (outputStrings[i] == null) {
					ComputerScreen.drawString(matrixStack, font,
							new StringTextComponent(" ").setStyle(TextStyle.OUTPUT), x,
							y + ((i - 1) * 10), 10);
				} else {
					ComputerScreen.drawString(matrixStack, font, new StringTextComponent(outputStrings[i])
							.setStyle(TextStyle.OUTPUT), x, y + ((i - 1) * 10), 10);
				}

			}
		}
	}

	// Output Screen
	public void clearOutput() {
		for (int i = 0; i > outputStrings.length; i++) {
			outputStrings[i] = null;
		}
	}

	public void updateLine(int l, String text) {
		System.out.println("Updating Line " + l + "with: " + text);
		outputStrings[l] = text;

	}

	public void getOutputFile() throws FileNotFoundException {
		ArrayList<String> result = new ArrayList<>();
		try (Scanner s = new Scanner(new FileReader(this.computer.getFileSystem().getEnvRootDir() + "\\.output"))) {
			while (s.hasNext()) {
				result.add(s.nextLine());
			}

		}
		int j = result.size();
		if (j > 16) {
			j = 16;
		}
		int k = 1;
		int l = 16;
		for (int i = j; i > 1; i--) {
			outputStrings[l] = result.get(result.size() - k);
			l--;
			k++;
		}
	}

	public void updateOutput(String text) {
		try {

			// Open given file in append mode by creating an
			// object of BufferedWriter class
			BufferedWriter out = new BufferedWriter(
					new FileWriter(this.computer.getFileSystem().getEnvRootDir() + "\\.output", true));

			// Writing on output stream
			out.write(text + "\n");
			// Closing the connection`
			out.close();
		}

		// Catch block to handle the exceptions
		catch (IOException e) {

			// Display message when exception occurs
			System.out.println("exception occurred" + e);
		}

	}

	private void initOutputString() {
		outputStrings = new String[18];
		for (String s : outputStrings) {
			s = null;
		}
	}
}
