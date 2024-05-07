package view.tool;

import init.D;
import init.sprite.SPRITES;
import init.sprite.UI.Icon;
import snake2d.util.map.MAP_SETTER;
import snake2d.util.sets.ArrayList;
import snake2d.util.sets.LIST;

public abstract class PLACER_TYPE {

    static {
        D.gInit(PLACER_TYPE.class);
    }

    public final static PLACER_TYPE SQUARE = new PLACER_TYPE(true, false, D.g("rectangle")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_rec;
        }

        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {
            int x11 = Math.min(x1, x2);
            int x22 = Math.max(x1, x2);
            int y11 = Math.min(y1, y2);
            int y22 = Math.max(y1, y2);
            for (int y = y11; y <= y22; y++)
                for (int x = x11; x <= x22; x++)
                    area.set(x, y);
        }

    };
    public final static PLACER_TYPE BRUSH = new PLACER_TYPE(false, true, D.g("brush")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_brush;
        }

        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {

            size += 1;
            int min = size / 2;

            double s = size / 2.0;
            double s2 = s * s;
            double d = (size & 1) == 0 ? 0.5 : 0;

            for (int dy = -min; dy <= min; dy++) {
                for (int dx = -min; dx <= min; dx++) {
                    double dist = (dx + d) * (dx + d) + (dy + d) * (dy + d);
                    if (dist <= s2) {
                        area.set(x1 + dx, y1 + dy);
                    }
                }
            }
        }
    };

    public final static PLACER_TYPE LINE = new PLACER_TYPE(true, true, D.g("line")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_line;
        }

        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {

            if (x1 == x2 && y1 == y2) {
                area.set(x1, y1);
                return;
            }

            int dx = x2 - x1;
            int dy = y2 - y1;
            dx = dx < 0 ? -1 : (dx > 0 ? 1 : 0);
            dy = dy < 0 ? -1 : (dy > 0 ? 1 : 0);
            boolean startX = dy * dx >= 0;

            int newX = -dy;
            int newY = dx;
            dx = newX;
            dy = newY;

            int offX1 = 0;
            int offY1 = 0;

            {
                for (int i = 1; i <= size / 2; i+=2) {
                    if ((i & 1) == 1) {
                        x1 -= dx;
                        x2-= dx;
                        y1 -= dy;
                        y2 -= dy;
                    }

                }
            }

            for (int i = 0; i <= size; i++) {

                drawLine(x1 + offX1, y1 + offY1, x2 + offX1, y2 + offY1, area, (i & 1) == 0);
                if (startX) {
                    offX1 += dx;
                } else {
                    offY1 += dy;
                }
                startX = !startX;
            }

        }

        private void drawLine(int x1, int y1, int x2, int y2, MAP_SETTER area, boolean first) {
            int dx = x2 - x1;
            int dy = y2 - y1;
            dx = dx < 0 ? -1 : (dx > 0 ? 1 : 0);
            dy = dy < 0 ? -1 : (dy > 0 ? 1 : 0);
            // first |= dx < 0 || dy < 0;
            if (first)
                area.set(x1, y1);
            else {
                // x2-=dx;
                // y2-=dy;
            }
            while (x1 != x2 || y1 != y2) {
                if (x1 != x2) {
                    x1 += dx;
                }

                if (y1 != y2) {
                    y1 += dy;
                }
                area.set(x1, y1);
            }

        }

    };


    public final static PLACER_TYPE OVAL = new PLACER_TYPE(true, false, D.g("ellipse")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_ellispse;
        }

        // modded
        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {
            CustomPlacers cls = new CustomPlacers();
            cls.Ellipse(x1, y1, x2, y2, size, area);
        }
    };

    public final static PLACER_TYPE OVAL_HOLLOW = new PLACER_TYPE(true, true, D.g("hollow ellipse")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_ellispse;
        }

        // modded
        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {
            CustomPlacers cls = new CustomPlacers();
            cls.hollowEllipse(x1, y1, x2, y2, size, area);
        }
    };

    public final static PLACER_TYPE HEXAGON_HOLLOW = new PLACER_TYPE(true, true, D.g("hollow hexagon")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_ellispse;
        }

        // modded
        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {
            CustomPlacers cls = new CustomPlacers();
            cls.hollowHexagon(x1, y1, x2, y2, size, area);
        }
    };

    public final static PLACER_TYPE HEXAGON = new PLACER_TYPE(true, true, D.g("hexagon")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_ellispse;
        }

        // modded
        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {
            CustomPlacers cls = new CustomPlacers();
            cls.hexagon(x1, y1, x2, y2, size, area);
        }
    };

    public final static PLACER_TYPE SQUARE_HOLLOW = new PLACER_TYPE(true, true, D.g("hollow rectangle")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_rec_hollow;
        }

        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {

            int x11 = Math.min(x1, x2);
            int x22 = Math.max(x1, x2);
            int y11 = Math.min(y1, y2);
            int y22 = Math.max(y1, y2);

            while (size >= 0 && x11 <= x22 && y11 <= y22) {
                outline(x11, y11, x22, y22, area);
                x11++;
                x22--;
                y11++;
                y22--;
                size--;
            }

        }

        void outline(int x1, int y1, int x2, int y2, MAP_SETTER area) {
            for (int y = y1; y <= y2; y++) {
                if (y == y1 || y == y2) {
                    for (int x = x1; x <= x2; x++) {
                        area.set(x, y);
                    }
                } else {
                    area.set(x1, y);
                    area.set(x2, y);
                }
            }
        }
    };

    public final static PLACER_TYPE FILL = new PLACER_TYPE(false, true, D.g("fill")) {
        @Override
        Icon icon() {
            return SPRITES.icons().m.place_fill;
        }

        @Override
        void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {}

    };

    // modded
    public static final LIST<PLACER_TYPE> all = new ArrayList<>(SQUARE, BRUSH, LINE, OVAL, SQUARE_HOLLOW, FILL, OVAL_HOLLOW, HEXAGON, HEXAGON_HOLLOW);

    final boolean drag;
    final boolean usesSize;
    final CharSequence name;

    private PLACER_TYPE(boolean drag, boolean usesSize, CharSequence name) {
        this.drag = drag;
        this.name = name;
        this.usesSize = usesSize;
    }

    abstract void paint(int x1, int y1, int x2, int y2, int size, MAP_SETTER area);

    abstract Icon icon();


}
