package com.thoughtworks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.thoughtworks.parser.GalaxyUnitParser;

public class Test {

	public static void main(String[] args) throws Exception {
		GalaxyUnitParser.parse("glob is I");
		GalaxyUnitParser.parse("prok is V");
		GalaxyUnitParser.parse("pish is X");
		GalaxyUnitParser.parse("tegj is L");
		GalaxyUnitParser.parse("glob glob Silver is 34 Credits");
		GalaxyUnitParser.parse("glob prok Gold is 57800 Credits");
		GalaxyUnitParser.parse("pish pish Iron is 3910 Credits");
		GalaxyUnitParser.parse("how much is pish tegj glob glob ?");
		GalaxyUnitParser.parse("how many Credits is glob prok Silver ?");
		GalaxyUnitParser.parse("how many Credits is glob prok Gold ?");
		GalaxyUnitParser.parse("how many Credits is glob prok Iron ?");
		GalaxyUnitParser.parse("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
		StringBuilder sb = new StringBuilder();
		sb.append("");

		A a = new A();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("sohan.ser")));
		objectOutputStream.writeObject(a);
		objectOutputStream.writeObject(20);
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("sohan.ser")));
		A a1 = (A) objectInputStream.readObject();
		System.out.println(a);
		System.out.println(a1);
		System.out.println(a.getI() + ", " + a.getB().getI());
		System.out.println(a1.getI() + ", " + a1.getB().getI());
	}

	static class A implements Serializable {
		int i = 10;
		transient B b = new B(20);

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public B getB() {
			return b;
		}

		public void setB(B b) {
			this.b = b;
		}

		private void writeObject(ObjectOutputStream o) throws Exception {
			o.defaultWriteObject();
			o.writeObject(getB().getI());
		}

		private void readObject(ObjectInputStream o) throws Exception {
			o.defaultReadObject();
			int i = (int) o.readObject();
			setB(new B(i));
		}

	}

	static class B {
		int i = 20;

		B(int i) {
			this.i = i;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

	}
}
