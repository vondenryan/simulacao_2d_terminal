package com.simulacao_terminal.utils;

public class Utils {
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void resetConsolePosition() {
        System.out.println("\033[H");
    }
}
