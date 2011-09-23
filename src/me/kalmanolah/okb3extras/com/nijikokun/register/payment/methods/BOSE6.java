package me.kalmanolah.okb3extras.com.nijikokun.register.payment.methods;

import me.kalmanolah.okb3extras.com.nijikokun.register.payment.Method;

import org.bukkit.plugin.Plugin;

import cosine.boseconomy.BOSEconomy;

/**
 * BOSEconomy 6 Implementation of Method
 * 
 * @author Nijikokun <nijikokun@shortmail.com> (@nijikokun)
 * @copyright (c) 2011
 * @license AOL license <http://aol.nexua.org>
 */
@SuppressWarnings("deprecation")
public class BOSE6 implements Method {
	private BOSEconomy BOSEconomy;

	public BOSEconomy getPlugin() {
		return BOSEconomy;
	}

	public String getName() {
		return "BOSEconomy";
	}

	public String getVersion() {
		return "0.6.2";
	}

	public int fractionalDigits() {
		return 0;
	}

	public String format(double amount) {
		String currency = BOSEconomy.getMoneyNamePlural();
		if (amount == 1) {
			currency = BOSEconomy.getMoneyName();
		}
		return amount + " " + currency;
	}

	public boolean hasBanks() {
		return true;
	}

	public boolean hasBank(String bank) {
		return BOSEconomy.bankExists(bank);
	}

	public boolean hasAccount(String name) {
		return BOSEconomy.playerRegistered(name, false);
	}

	public boolean hasBankAccount(String bank, String name) {
		return BOSEconomy.isBankOwner(bank, name) || BOSEconomy.isBankMember(bank, name);
	}

	public MethodAccount getAccount(String name) {
		if (!hasAccount(name)) {
			return null;
		}
		return new BOSEAccount(name, BOSEconomy);
	}

	public MethodBankAccount getBankAccount(String bank, String name) {
		if (!hasBankAccount(bank, name)) {
			return null;
		}
		return new BOSEBankAccount(bank, BOSEconomy);
	}

	public boolean isCompatible(Plugin plugin) {
		return plugin.getDescription().getName().equalsIgnoreCase("boseconomy") && (plugin instanceof BOSEconomy) && plugin.getDescription().getVersion().equals("0.6.2");
	}

	public void setPlugin(Plugin plugin) {
		BOSEconomy = (BOSEconomy) plugin;
	}
	public class BOSEAccount implements MethodAccount {
		private final String name;
		private final BOSEconomy BOSEconomy;

		public BOSEAccount(String name, BOSEconomy bOSEconomy) {
			this.name = name;
			BOSEconomy = bOSEconomy;
		}

		public double balance() {
			return BOSEconomy.getPlayerMoney(name);
		}

		public boolean set(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			return BOSEconomy.setPlayerMoney(name, IntAmount, false);
		}

		public boolean add(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			return BOSEconomy.addPlayerMoney(name, IntAmount, false);
		}

		public boolean subtract(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			int balance = (int) balance();
			return BOSEconomy.setPlayerMoney(name, (balance - IntAmount), false);
		}

		public boolean multiply(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			int balance = (int) balance();
			return BOSEconomy.setPlayerMoney(name, (balance * IntAmount), false);
		}

		public boolean divide(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			int balance = (int) balance();
			return BOSEconomy.setPlayerMoney(name, (balance / IntAmount), false);
		}

		public boolean hasEnough(double amount) {
			return (balance() >= amount);
		}

		public boolean hasOver(double amount) {
			return (balance() > amount);
		}

		public boolean hasUnder(double amount) {
			return (balance() < amount);
		}

		public boolean isNegative() {
			return (balance() < 0);
		}

		public boolean remove() {
			return false;
		}
	}
	public class BOSEBankAccount implements MethodBankAccount {
		private final String bank;
		private final BOSEconomy BOSEconomy;

		public BOSEBankAccount(String bank, BOSEconomy bOSEconomy) {
			this.bank = bank;
			BOSEconomy = bOSEconomy;
		}

		public String getBankName() {
			return bank;
		}

		public int getBankId() {
			return -1;
		}

		public double balance() {
			return BOSEconomy.getBankMoney(bank);
		}

		public boolean set(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			return BOSEconomy.setBankMoney(bank, IntAmount, true);
		}

		public boolean add(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			int balance = (int) balance();
			return BOSEconomy.setBankMoney(bank, (balance + IntAmount), false);
		}

		public boolean subtract(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			int balance = (int) balance();
			return BOSEconomy.setBankMoney(bank, (balance - IntAmount), false);
		}

		public boolean multiply(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			int balance = (int) balance();
			return BOSEconomy.setBankMoney(bank, (balance * IntAmount), false);
		}

		public boolean divide(double amount) {
			int IntAmount = (int) Math.ceil(amount);
			int balance = (int) balance();
			return BOSEconomy.setBankMoney(bank, (balance / IntAmount), false);
		}

		public boolean hasEnough(double amount) {
			return (balance() >= amount);
		}

		public boolean hasOver(double amount) {
			return (balance() > amount);
		}

		public boolean hasUnder(double amount) {
			return (balance() < amount);
		}

		public boolean isNegative() {
			return (balance() < 0);
		}

		public boolean remove() {
			return BOSEconomy.removeBank(bank);
		}
	}
}