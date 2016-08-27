package sample;

class BoxXML {
    int top;
    int left;
    int width;
    int height;

    BoxXML(int left, int top, int width, int height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "<box top='" + top + "' left='" + left + "' width='" + width + "' height='" + height + "'/>\n";
    }
}
