import java.util.*;

public class FactoryImpl implements Factory {
    private Holder first;
    private Holder last;
    private Integer size;

    public FactoryImpl(){
        first = null;
        last = null;
        size = 0;
    }

    private boolean isEmpty(){      // Controls if the factory line is empty
        return size==0;
    }

    @Override
    public void addFirst(Product product) {
        Holder newHolder = new Holder(null, product,null); // First, program creates new holder to hold new product

        if(isEmpty()){         // When line is empty, new product becomes first and last product of the line
            first = newHolder;
            last = newHolder;
        }else{
            newHolder.setNextHolder(first);     // Otherwise, new product becomes first product and its next holder becomes old first
            first.setPreviousHolder(newHolder);
            first = newHolder;
        }
        size++;     // In all add methods, program increases size of the line by 1
    }

    @Override
    public void addLast(Product product) {
        Holder newHolder = new Holder(null, product, null);
        if(isEmpty()){      // When line is empty, new product becomes first and last product of the line
            first = newHolder;
            last = newHolder;
        }else {
            newHolder.setPreviousHolder(last);      // Otherwise, new product becomes last product and its previous holder becomes old last
            last.setNextHolder(newHolder);
            last = newHolder;
        }
        size++;
    }

    @Override
    public Product removeFirst() throws NoSuchElementException {
        Holder oldFirst = first;        // It stores the holder to be removed, to return the old product after remove it
        if(isEmpty()){      // If the line is empty, it throws exception
            throw new NoSuchElementException();
        }else if(size == 1){        // When line has only 1 element, program assigns the first and last variables to null
            first = null;           // and line becomes empty
            last = null;
            size--;
        }else {
            Holder newFirst = first.getNextHolder();    // Otherwise, it assigns the product which is after the first
            newFirst.setPreviousHolder(null);           // product to the first and set its previous product to null
            first = newFirst;
            size--;
        }
        return oldFirst.getProduct();
    }

    @Override
    public Product removeLast() throws NoSuchElementException {
        Holder oldLast = last;      // It stores the holder to be removed, to return the old product after remove it
        if(isEmpty()){      // If the line is empty, it throws exception
            throw new NoSuchElementException();
        }else if(size == 1){    // Again, when it has 1 element, it removes it and line is clear
            first = null;
            last = null;
            size--;
        }else{
            Holder newLast = last.getPreviousHolder();  // Otherwise, it removes the last product
            newLast.setNextHolder(null);
            last = newLast;
            size--;
        }
        return oldLast.getProduct();
    }

    @Override
    public Product find(int id) throws NoSuchElementException {
        if(searchProduct(id) == null){      // Calls search product method and returns it
            throw new NoSuchElementException();
        }
        return searchProduct(id);
    }

    private Product searchProduct(int id) throws NoSuchElementException{
        Holder holder = first;
        for (int i = 0; i < size; i++) {        // It traverses through line and find the product with specified id
            if(id == holder.getProduct().getId()){
                return holder.getProduct();
            }else{
                holder = holder.getNextHolder();
            }
        }
        return null;
    }

    @Override
    public Product update(int id, Integer value) throws NoSuchElementException {
        Product oldProduct;     // Method searches the product and if it finds then it updates the value with new one
        Product product;
        if(searchProduct(id) == null){  // If the product with given id is not in line it throws an exception
            throw new NoSuchElementException();
        }else{
            product = searchProduct(id);
            oldProduct = new Product(product.getId(), product.getValue());
            searchProduct(id).setValue(value);
        }
        return oldProduct;     // Then it returns the old product
    }

    private Holder searchHolder(int index){
        Holder holder = first;
        for (int i = 0; i < size; i++) {    // This method traverses through the list and finds the holder with given index
            if(index == i){
                return holder;
            }else{
                holder = holder.getNextHolder();
            }
        }
        return null;
    }

    private Product searchIndex(int index) throws IndexOutOfBoundsException{
        if(index<0 || index > size-1){      // This method uses searchHolder method and return the product
            throw new IndexOutOfBoundsException(); // If given index is out of bounds, it throws exception
        }
        return searchHolder(index).getProduct();
    }

    @Override
    public Product get(int index) throws IndexOutOfBoundsException {
        if(index > size){       // This method finds the product with given index and returns it
            throw new IndexOutOfBoundsException();
        }
        return searchIndex(index);
    }

    @Override
    public void add(int index, Product product) throws IndexOutOfBoundsException {
        Holder holder = new Holder(null, product, null); // Add method create a holder with inputs
        if(index<0 || index > size){        // Then checks the index
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){     // When given index is 0, it uses addFirst method
            addFirst(product);
        }else if(index == size){    // When index is after the last element, it uses the addLast
            addLast(product);
        }else {         // Otherwise, it changes the next and previous holders of product with given index
                        // and bonds the new holder between the holder by setting holders of it
            Holder oldHolder = searchHolder(index);
            oldHolder.getPreviousHolder().setNextHolder(holder);
            holder.setPreviousHolder(oldHolder.getPreviousHolder());
            oldHolder.setPreviousHolder(holder);
            holder.setNextHolder(oldHolder);
            size++;     // Then it increases the size
        }
    }

    @Override
    public Product removeIndex(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index > size-1){    // It first checks the index
            throw new IndexOutOfBoundsException();
        }
        Holder holder = searchHolder(index);
        if(index == 0){     // If index is first element of the line, it calls removeFirst method
            removeFirst();
        }else if(index == size-1){      // If it is the last element, it calls removeLast method
            removeLast();
        }else{      // Otherwise, it removes the product by changing next holder of its previous holder
                    // and previous holder of its next holder
            holder.getPreviousHolder().setNextHolder(holder.getNextHolder());
            holder.getNextHolder().setPreviousHolder(holder.getPreviousHolder());
            size--;
        }
        return holder.getProduct();
    }

    private Holder searchValue(int value) throws NoSuchElementException{
        Holder holder = first;
        if (isEmpty()){     //This method, like other search methods, finds the holder with given value
            throw new NoSuchElementException();
        }else {
            for (int i = 0; i < size; i++) {
                if (value == holder.getProduct().getValue()) {
                    return holder;
                } else {
                    if (holder.getNextHolder() == null) {
                        throw new NoSuchElementException();
                    } else {
                        holder = holder.getNextHolder();
                    }
                }
            }
        }
        return null;
    }


    @Override
    public Product removeProduct(int value) throws NoSuchElementException {
        Holder removedHolder = searchValue(value);      //  This method removes product by using its value
        if(removedHolder == first){     // If it is the first holder, it removes it with removeFirst method
            removeFirst();
        }else if(removedHolder == last){    // If it is last holder, it removes it with removeLast method
            removeLast();
        }else {     // Otherwise, it removes by assigning previous to next and next to previous holder
            removedHolder.getPreviousHolder().setNextHolder(removedHolder.getNextHolder());
            removedHolder.getNextHolder().setPreviousHolder(removedHolder.getPreviousHolder());
            size--;
        }
        return removedHolder.getProduct();
    }

    @Override
    public int filterDuplicates() {         // This method stores values of products in a set and
        int process = 0;                    // if it is already in the set it removes it and all products in the line
        HashSet<Integer> values = new HashSet<>();  // become unique
        Holder holder = first;
        for (int i = 0; i < size; i++) {
            if(!values.contains(holder.getProduct().getValue())){
                values.add(holder.getProduct().getValue());
            }else {
                removeIndex(i);
                process++;
                i--;
            }
            holder = holder.getNextHolder();
        }
        return process;
    }

    @Override
    public void reverse() {         // Reverse method reverse the factory line by changing holders of products
        Holder holder = first;      // It assigns the next holder of product to previous and when it comes indexes,
        for (int i = 0; i < size; i++) {  // it changes the first and last products
            Holder oldNext = holder.getNextHolder();
            holder.setNextHolder(holder.getPreviousHolder());
            holder.setPreviousHolder(oldNext);
            if(i == 0){
                last = holder;
            }else if(i == size-1){
                first = holder;
            }
            holder = holder.getPreviousHolder();
        }
    }

    public String toString(){       // To string method to print the factory line
        Holder holder = first;
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < size; i++) {
            if(i == size-1) {
                sb.append(holder.getProduct().toString());
            }else {
                sb.append(holder.getProduct().toString()).append(",");
            }
            holder = holder.getNextHolder();
        }
        sb.append("}\n");
        return new String(sb);
    }
}