package view.tool;


import snake2d.util.map.MAP_SETTER;
import view.keyboard.KEYS;

import java.util.ArrayList;
import java.util.List;


public class CustomPlacers {
    public void paintEllipse(int x1, int y1, int x2, int y2, int size, boolean hollow, MAP_SETTER area) {
        if (x1 == x2 && y1 == y2) {
            area.set(x1, y1);
        }

        int x11 = Math.min(x1, x2);
        int x22 = Math.max(x1, x2);
        int y11 = Math.min(y1, y2);
        int y22 = Math.max(y1, y2);

        double width = x22 - x11;
        double height = y22 - y11;


        if (KEYS.MAIN().MOD.isPressed()) {
            width = Math.max(width, height);
            height = Math.max(width, height);
        }

        double divisor = Math.max(width, height);
        double r2 = Math.max(width, height);

        r2 *= r2;
        for (double y = -height; y <= height; y++) {
            for (double x = -width; x <= width; x++) {
                double distX = x * (divisor / width);
                double distY = y * (divisor / height);
                double r = distX * distX + distY * distY;
                if (hollow ? (Math.abs(Math.sqrt(r) - Math.sqrt(r2)) < (1+size) && (r <= r2)): (r <= r2) ) {
                    area.set((int) (x11 + x), (int) (y11+y));
                }
            }
        }
    }

    public void hollowEllipse(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {
        this.paintEllipse(x1, y1, x2, y2, size, true, area);
    }

    public void Ellipse(int x1, int y1, int x2, int y2, int size, MAP_SETTER area) {
        this.paintEllipse(x1, y1, x2, y2, size, false, area);
    }
}

