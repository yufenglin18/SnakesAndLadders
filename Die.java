import java.util.Random;
public class Die {
    //Class data (constants)

    /** The default number of faces on a {@code Die}. */
    public static final int DEFAULT_FACES = 6;
    /** Minimum possible number of faces on a die with flat faces. */
    public static final int MIN_FACES = 4;

    //Instance data

    /** The number of faces on the die. */
    private int numFaces;
    /** The currently displayed face (the side that is 'up'). */
    private int faceValue;
    /** Source of next face. */
    private Random generator;

    //Constructors

    /**
     * Creates a new Die with the {@linkplain #DEFAULT_FACES default number} of
     * faces (6) and an initial face value of 1.
     */
    public Die() {
        // The this keyword can be used to call another of the class's constructors.
        // It must be the first statement in the calling constructor.
        this(DEFAULT_FACES);
    }

    /**
     * Creates a new Die with the given number of faces and an initial value
     * of 1. If the requested number of faces is below the {@linkplain
     * #MIN_FACES minimum} then it is set to the default number of faces (6).
     *
     * @param faces the number of faces of the new Die
     */
    public Die(int faces) {
        if (faces >= MIN_FACES) {
            numFaces = faces;
        } else {
            numFaces = DEFAULT_FACES;
        }
        faceValue = 1;
        generator = new Random();
    }

    //Getters and modifiers

    /**
     * Returns the Die's current face value (i.e., the side facing 'up').
     *
     * @return the current face value
     */
    public int getFaceValue() {
        return faceValue;
    }

    /**
     * Simulates rolling the die by generating a new face number between 1 and
     * numFaces. Returns the new face.
     *
     * @return the new value of the Die after the roll
     */
    public int roll() {
        faceValue = generator.nextInt(numFaces) + 1;
        return faceValue;
    }

    /**
     * Returns the number of faces on the Die.
     * @return the number of faces
     */
    public int getFaces() {
        return numFaces;
    }
}
