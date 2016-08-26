package sample;

class BoxXML {
    private int top, left, width, height;

    public BoxXML(int left, int top, int width, int height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<box top='");
        sb.append(top);
        sb.append("' left='");
        sb.append(left);
        sb.append("' width='");
        sb.append(width);
        sb.append("' height='");
        sb.append(height);
        sb.append("'/>\n");

        return sb.toString();
    }
}
