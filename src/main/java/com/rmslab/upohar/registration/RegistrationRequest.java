package com.rmslab.upohar.registration;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName ;
    private final String lastName ;
    private final String email ;
    private final String password ;
    private final String dob ;
    private final String address ;
}
