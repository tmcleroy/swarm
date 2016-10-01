public class Main {

    public static void main(String[] args) {
        Hex h = new Hex(1, 1, -2);
        System.out.println(h.neighbors(h));

        Grid grid = new Grid(10, 10);
        System.out.println(grid.cells);

        System.out.println(grid.getCell(h));
    }

}