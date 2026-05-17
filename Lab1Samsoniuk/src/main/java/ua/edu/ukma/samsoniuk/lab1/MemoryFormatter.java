package ua.edu.ukma.samsoniuk.lab1;

public class MemoryFormatter {
    public static String formatMemory(long bytes) {
        if (bytes < 0) {
            return "Invalid: must be positive";
        }

        if (bytes == 0) {
            return "0 B";
        }
        if (bytes >= 1073741824) {
            double gb = bytes / 1073741824.0;
            return String.format("%.1f GB", gb);
        }
        if (bytes >= 1048576) {
            double mb = bytes / 1048576.0;
            return String.format("%.1f MB", mb);
        }
        if (bytes >= 1024) {
            double kb = bytes / 1024.0;
            return String.format("%.1f KB", kb);
        }

        return bytes + " B";
    }
}
