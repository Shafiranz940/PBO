import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Field {
    private Object[][] field;

    public Field(int row, int col) {
        field = new Object[row][col];
    }

    public void clear(Location location) {
        field[location.getRow()][location.getCol()] = null;
    }

    public void place(Object object, Location location) {
        field[location.getRow()][location.getCol()] = object;
    }

    public void clearAll() {
        for (int r = 0; r < field.length; r++) {
            for (int c = 0; c < field[r].length; c++) {
                field[r][c] = null;
            }
        }
    }

    public Object getObjectAt(Location location) {
        return field[location.getRow()][location.getCol()];
    }

    public List<Location> getAllFreeAdjacentLocations(Location location) {
        List<Location> free = new ArrayList<>();
        for (Location loc : adjacentLocations(location)) {
            if (getObjectAt(loc) == null) {
                free.add(loc);
            }
        }
        return free;
    }

    public List<Location> adjacentLocations(Location location) {
        List<Location> locations = new ArrayList<>();
        int row = location.getRow();
        int col = location.getCol();

        for (int roff = -1; roff <= 1; roff++) {
            for (int coff = -1; coff <= 1; coff++) {
                if (roff == 0 && coff == 0) continue;

                int newRow = row + roff;
                int newCol = col + coff;

                if (newRow >= 0 && newRow < getRow() && newCol >= 0 && newCol < getCol()) {
                    locations.add(new Location(newRow, newCol));
                }
            }
        }

        Collections.shuffle(locations);
        return locations;
    }

    public int getRow() { return field.length; }
    public int getCol() { return field[0].length; }
}