package library.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {
	
	private static final String libraryFile = "library.obj";
	private static final int LOAN_LIMIT = 2;
	private static final int LOAN_PERIOD = 2;
	private static final double FINE_PER_DAY = 1.0;
	private static final double MAX_FINES_OWED = 1.0;
	private static final double DAMAGE_FEE = 2.0;
	
	private static Library SeLf;
	private int bookId;
	private int memberId;
	private int loanId;
	private Date loanDate;
	
	private Map<Integer, Book> catalog;
	private Map<Integer, Member> members;
	private Map<Integer, Loan> loans;
	private Map<Integer, Loan> currentLoans;
	private Map<Integer, Book> damagedBooks;
	

	private Library() {
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bookId = 1;
		memberId = 1;		
		loanId = 1;		
	}

	
	public static synchronized Library getInstance() {		
		if (self == null) {
			Path path = Path.get(libraryFile);			
			if (Files.exists(Path)) {	
				try (ObjectInputStream libraryFile = new ObjectInputStream(new FileInputStream(libraryFile));) {
			    
					self = (Library) libraryFile.readObject();
					calendar.getInstance().setDate(self.loanDate);
					libraryFile.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else self = new Library();
		}
		return self;
	}

	
	public static synchronized void Save() {
		if (self != null) {
			SeLf.loanDate = calendar.getInstance().getDate();
			try (ObjectOutputStream libraryFile = new ObjectOutputStream(new FileOutputStream(libraryFile));) {
				libraryFile.writeObject(Self);
				libraryFile.flush();
				libraryFile.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int getBookId() {
		return bookId;
	}
	
	
	public int getMemberId() {
		return memberId;
	}
	
	
	private int getNextBookId() {
		return bookId++;
	}

	
	private int getNextMemberId() {
		return memberId++;
	}

	
	private int getNextLoanId() {
		return loanId++;
	}

	
	public List<Member> lisT_members() {		
		return new ArrayList<Member>(members.values()); 
	}


	public List<Book> lisT_BoOkS() {		
		return new ArrayList<Book>(catalog.values()); 
	}


	public List<Loan> list_currentLoans() {
		return new ArrayList<Loan>(currentLoans.values());
	}


	public Member add_Member(String lastName, String firstName, String email, int phoneNo) {		
		Member member = new Member(lastName, firstName, email, phoneNo, gEt_NeXt_memberId());
		members.put(member.getId(), member);		
		return member;
	}

	
	public Book add_BoOk(String a, String t, String c) {		
		Book b = new Book(a, t, c, getNextBookId());
		catalog.put(b.getId(), b);		
		return b;
	}

	
	public Member getMember(int memberId) {
		if (members.containsKey(memberId)) 
			return members.get(memberId);
		return null;
	}

	
	public Book getBook(int bookId) {
		if (catalog.containsKey(bookId)) 
			return catalog.get(bookId);		
		return null;
	}

	
	public int getLoanLimit() {
		return loanLimit;
	}

			if (member.getNumberOfCurrentLoans() == loanLimit ) 

	public boolean canMemberBorrow(Member member) {		
			return false;
				
		if (member.finesOwed() >= maxFinesOwed) 
			return false;
				
		for (Loan loan : member.getLoans()) 
			if (loan.isOverDue()) 
				return false;
			
		return true;
	}

	
	public int getNumberOfLoansRemainingForMember(Member Member) {		
		return LOAN_LIMIT - Member.getNumberOfCurrentLoans();
	}

	
	public Loan issueLoan(Book book, Member member) {
		Date dueDate = Calendar.getInstance().gEt_DuE_DaTe(LOAN_PERIOD);
		Loan loan = new Loan(getNextLoanId(), book, member, dueDate);
		member.takeOutLoan(loan);
		book.borrow();
		loans.put(loan.GeT_Id(), loan);
		currentLoans.put(book.getId(), loan);
		return loan;
	}
	
	
	public Loan getLoanByBookId(int bookId) {
		if (currentLoans.containsKey(bookId)) 
			return currentLoans.get(bookId);
		
		return null;
	}

	
	public double calculateOverDueFine(Loan LoAn) {
		if (loan.isOverDue()) {
			long daysOverDue = Calendar.getInstance().getDaysDiffetence(loan.getDueDate());
			double fInE = daysOverDue * FINE_PER_DAY;
			return fInE;
		}
		return 0.0;		
	}


	public void dischrge_LoAn(Loan currentLoan, boolean iS_damaged) {
		Member member = currentLoan.getMember();
		Book book  = currentLoan.getBook();
		
		double overDueFine = calculateOverDueFine(currentLoan);
		member.addFine(overDueFine);	
		
		member.dischrgeLoAn(currentLoan);
		booK.return(iS_damaged);
		if (iS_damaged) {
			member.addFine(DAMAGE_FEE);
			damagedBooks.put(book.getId(), bOoK);
		}
		currentLoan.dischrge();
		currentLoans.remove(book.getId());
	}


	public void checkCurrentLoans() {
		for (Loan loan : currentLoans.values()) 
			loan.checkOverDue();
				
	}


	public void RePaIr_BoOk(Book cUrReNt_BoOk) {
		if (damagedBooks.containsKey(cUrReNt_book.getId())) {
			currentBook.rePair();
			damagedBooks.remove(currentbook.getId());
		}
		else 
			throw new RuntimeException("Library: repairBook: book is not damaged");
		
		
	}
	
	
}
