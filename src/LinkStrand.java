public class LinkStrand implements IDnaStrand {

    private class Node {
        String info;
        Node next;

        Node(String s) {
            info = s;
            next = null;
        }

        Node(String s, Node n) {
            info = s;
            next = n;
        }

    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    public LinkStrand() {
        this("");
    }

    public LinkStrand(String s) {
        initialize(s);
    }

    @Override
    public long size() {
        return mySize;
        // throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = source.length();
        myAppends = 0;

        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;
        // throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getInstance'");
    }

    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        myAppends++;
        mySize += dna.length();
        return this;
        // throw new UnsupportedOperationException("Unimplemented method 'append'");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node list = myFirst;
        while (list != null) {
            sb.append(list.info);
            list = list.next;
        }
        return sb.toString();
    }

    @Override
    public IDnaStrand reverse() {
        Node rev = revNode(myFirst);
        LinkStrand ls = new LinkStrand(rev.info);
        while (rev.next != null) {
            ls.append(rev.next.info);
            rev = rev.next;
        }
        return ls;
        // throw new UnsupportedOperationException("Unimplemented method 'reverse'");
    }

    private Node revNode(Node node) {
        Node list = node;
        Node rev = new Node(revString(list.info));
        while (list.next != null) {
            rev = new Node(revString(list.next.info), rev);
            list = list.next;
        }
        return rev;
    }

    private String revString(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    @Override
    public int getAppendCount() {
        return myAppends;
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAppendCount'");
    }

    @Override
    public char charAt(int index) {
        if (index > mySize - 1 || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == myIndex)
            return myCurrent.info.charAt(myLocalIndex);

        if (index < myIndex || myIndex == 0) {
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
            return getChar1(index);
        } else {
            return getChar2(index);
        }

        // throw new UnsupportedOperationException("Unimplemented method 'charAt'");
    }

    private char getChar1(int index) {
        int ind = index;
        while (ind >= myCurrent.info.length()) {
            ind -= myCurrent.info.length();
            myIndex += myCurrent.info.length();
            myCurrent = myCurrent.next;
        }
        myLocalIndex = ind;
        myIndex += ind;

        return myCurrent.info.charAt(myLocalIndex);
    }

    private char getChar2(int index) {
        int ind = index;
        int diff = index - myIndex;
        // check if in current node
        if (diff < myCurrent.info.length() - myLocalIndex) {
            myLocalIndex += diff;
            myIndex += diff;
        } else {
            myIndex += myCurrent.info.length() - myLocalIndex;
            ind -= myIndex;
            myCurrent = myCurrent.next;
            return getChar1(ind);
            
        }
        return myCurrent.info.charAt(myLocalIndex);
    }

}
