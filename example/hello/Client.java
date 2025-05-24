/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 * Neither the name of Oracle nor the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */
package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Client <host>");
            return;
        }

        try {
            Registry registry = LocateRegistry.getRegistry(args[0]);
            Hello stub = (Hello) registry.lookup("Hello");

            Scanner sc = new Scanner(System.in);
            System.out.println("=== Calculadora Remota ===");
            System.out.println("Comandos: add, subtract, multiply, divide (ex: add 1 2 3)");
            System.out.println("Digite 'exit' para sair.");

            while (true) {
                System.out.print("> ");
                String line = sc.nextLine();
                if (line.equalsIgnoreCase("exit")) break;

                String[] parts = line.trim().split("\\s+");
                String command = parts[0];
                List<Integer> numbers = Arrays.stream(parts).skip(1)
                        .map(Integer::parseInt).collect(Collectors.toList());

                switch (command) {
                    case "add":
                        System.out.println("Resultado: " + stub.add(numbers));
                        break;
                    case "subtract":
                        System.out.println("Resultado: " + stub.subtract(numbers));
                        break;
                    case "multiply":
                        System.out.println("Resultado: " + stub.multiply(numbers));
                        break;
                    case "divide":
                        System.out.println("Resultado: " + stub.divide(numbers));
                        break;
                    default:
                        System.out.println("Comando desconhecido.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
