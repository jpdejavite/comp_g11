JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Grasp.java \
        Station.java \
        StationList.java \
        SaveWorld.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class